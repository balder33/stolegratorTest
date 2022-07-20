package api;

import api.requests.AuthorizationRequest;
import api.requests.ClientCredentialsRequest;
import api.requests.RegisterPlayerRequest;
import api.responses.ClientCredentials;
import api.responses.NotFoundError;
import api.responses.Player;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;

public class Tests {

    private final static String URL = "http://test-api.d6.dev.devcaz.com";
    private final static String basicToken = "ZnJvbnRfMmQ2YjBhODM5MTc0MmY1ZDc4OWQ3ZDkxNTc1NWUwOWU6";
    public static String bearerToken;

    // перед тестами нам нужен bearerToken
    @Before
    public void Setup(){
        ClientCredentialsRequest request =
                new ClientCredentialsRequest("client_credentials", "guest:default");
        ClientCredentials response = given()
                .body(request)
                .when()
                .header("Authorization", "Basic" + " " + basicToken)
                .contentType(ContentType.JSON)
                .baseUri(URL)
                .post("/v2/oauth2/token")
                .then().log().all()
                .statusCode(200)
                .extract().as(ClientCredentials.class);

        bearerToken = response.getAccess_token();
    }

    /**
     * Get guest bearer-token test
    **/
    @Test
    public void getClientCredentialsGrantTest(){
        ClientCredentialsRequest request =
                new ClientCredentialsRequest("client_credentials", "guest:default");

        ClientCredentials response = given()
                .body(request)
                .when()
                .header("Authorization", "Basic" + " " + basicToken)
                .contentType(ContentType.JSON)
                .baseUri(URL)
                .post("/v2/oauth2/token")
                .then().log().all()
                .statusCode(200)
                .extract().as(ClientCredentials.class);

        Assert.assertNotNull(response.getToken_type());
        Assert.assertNotNull(response.getExpires_in());
        Assert.assertNotNull(response.getAccess_token());
        Assert.assertNull(response.getRefresh_token());

        Assert.assertEquals("Bearer", response.getToken_type());
    }


    // тест падает при указании корректных currency_code по ISO4217.
    // баг в методе или неверное описание в документации
    /**
     * New player registration test
    **/
    @Test
    public void registerNewPlayerTest(){

        // объявляем нужные переменные
        Faker faker = new Faker();
        String username = faker.animal().name() + faker.number().numberBetween(100, 999);
        String email = faker.internet().safeEmailAddress();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String pass = "amFuZWRvZTEyMw==";

        RegisterPlayerRequest request = new RegisterPlayerRequest(
                username, pass, pass, email, firstName, lastName, "3434");

        Player response = given()
                .body(request)
                .when()
                .header("Authorization", "Bearer" + " " + bearerToken)
                .contentType(ContentType.JSON)
                .baseUri(URL)
                .post("/v2/players")
                .then().log().all()
                .extract().as(Player.class);

        Assert.assertNotNull(response.getId());
        Assert.assertNotNull(response.getUsername());
        Assert.assertNotNull(response.getEmail());
        Assert.assertNotNull(response.getName());
        Assert.assertNotNull(response.getSurname());

        Assert.assertEquals(username, response.getUsername());
        Assert.assertEquals(email, response.getEmail());
        Assert.assertEquals(firstName, response.getName());
        Assert.assertEquals(lastName, response.getSurname());
    }

    /**
     * Player authorization test
     **/
    @Test
    public void authorizationByPlayer(){

        // объявляем нужные переменные
        Faker faker = new Faker();
        String username = faker.animal().name() + faker.number().numberBetween(100, 999);
        String email = faker.internet().safeEmailAddress();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String pass = "amFuZWRvZTEyMw==";

        // Сначала регистрируем игрока
        RegisterPlayerRequest registerRequest = new RegisterPlayerRequest(
                username, pass, pass,
                email, firstName, lastName,
                null);

        Player registerResponse = given()
                .baseUri(URL)
                .header("Authorization", "Bearer " + bearerToken)
                .body(registerRequest)
                .contentType(ContentType.JSON)
                .when()
                .post("/v2/players")
                .then().log().all()
                .statusCode(201)
                .extract().as(Player.class);

        Assert.assertNotNull(registerResponse.getId());
        Assert.assertNotNull(registerResponse.getUsername());
        Assert.assertNotNull(registerResponse.getEmail());
        Assert.assertNotNull(registerResponse.getName());
        Assert.assertNotNull(registerResponse.getSurname());

        // авторизуемся под созданным игроком
        AuthorizationRequest authRequest = new AuthorizationRequest(
                "password", username, pass);

        ClientCredentials authResponse = given()
                .baseUri(URL)
                .body(authRequest)
                .contentType(ContentType.JSON)
                .header("Authorization", "Basic " + basicToken)
                .when()
                .post("/v2/oauth2/token")
                .then().log().all()
                .statusCode(200)
                .extract().as(ClientCredentials.class);

        Assert.assertNotNull(authResponse.getToken_type());
        Assert.assertNotNull(authResponse.getExpires_in());
        Assert.assertNotNull(authResponse.getAccess_token());
        Assert.assertNotNull(authResponse.getRefresh_token());

        Assert.assertEquals("Bearer", authResponse.getToken_type());
    }

    // тест фэйлится! метод не возвращает сущность по идентификатору.
    // баг в методе или неверное описание в документации
    /**
     * Get player test
    **/
    @Test
    public void getPlayerTest(){

        // объявляем нужные переменные
        Faker faker = new Faker();
        String username = faker.animal().name() + faker.number().numberBetween(100, 999);
        String email = faker.internet().safeEmailAddress();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String pass = "amFuZWRvZTEyMw==";

        // Сначала регистрируем игрока
        RegisterPlayerRequest registerRequest = new RegisterPlayerRequest(
                username, pass, pass,
                email, firstName, lastName,
                null);

        Player registerResponse = given()
                .baseUri(URL)
                .header("Authorization", "Bearer " + bearerToken)
                .body(registerRequest)
                .contentType(ContentType.JSON)
                .when()
                .post("/v2/players")
                .then().log().all()
                .statusCode(201)
                .extract().as(Player.class);

        Assert.assertNotNull(registerResponse.getId());
        Assert.assertNotNull(registerResponse.getUsername());
        Assert.assertNotNull(registerResponse.getEmail());
        Assert.assertNotNull(registerResponse.getName());
        Assert.assertNotNull(registerResponse.getSurname());

        String path = String.format("/v2/players/%s", registerResponse.getId().toString());

        Player getPlayerResponse = given()
                .when()
                .header("Authorization", "Bearer" + " " + bearerToken)
                .contentType(ContentType.JSON)
                .baseUri(URL)
                .get(path)
                .then().log().all()
                .extract().as(Player.class);

        Assert.assertNotNull(getPlayerResponse.getId());
        Assert.assertNotNull(getPlayerResponse.getUsername());
        Assert.assertNotNull(getPlayerResponse.getEmail());
        Assert.assertNotNull(getPlayerResponse.getName());
        Assert.assertNotNull(getPlayerResponse.getSurname());

        Assert.assertEquals(username, getPlayerResponse.getUsername());
        Assert.assertEquals(email, getPlayerResponse.getEmail());
        Assert.assertEquals(firstName, getPlayerResponse.getName());
        Assert.assertEquals(lastName, getPlayerResponse.getSurname());
    }

    /**
     * Get non-existent player test
     **/
    @Test
    public void getAnotherPlayerTest(){
        NotFoundError getPlayerError = given()
                .when()
                .header("Authorization", "Bearer" + " " + bearerToken)
                .contentType(ContentType.JSON)
                .baseUri(URL)
                .get("/v2/players/00")
                .then().log().all()
                .extract().as(NotFoundError.class);

        Assert.assertEquals("Not Found", getPlayerError.getName());
        Assert.assertEquals("0", getPlayerError.getCode().toString());
        Assert.assertEquals("404", getPlayerError.getStatus().toString());
    }

    @Test
    public void getDate() {
        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String text = "2019-05-13 10:00:52";
        String text2 = "2019-05-13 10:00:53";
        LocalDateTime dateTime = LocalDateTime.parse(text, formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(text2, formatter);
        Assert.assertTrue("123", dateTime2.isAfter(dateTime));
    }
}
