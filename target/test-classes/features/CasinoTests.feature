# language: en

  Feature: Casino tests

    Scenario: Authorization test
      Given user open authorization page
      When in field "Login" user enter the text "admin1"
      And in field "Password" user enter the text "[9k<k8^z!+$$GkuP"
      And click 'Sign in' button
      Then url is "http://test-app.d6.dev.devcaz.com/configurator/dashboard/index"
      And admin panel loaded

    Scenario: Players page test
      Given user authorized in the admin panel
      And url is "http://test-app.d6.dev.devcaz.com/configurator/dashboard/index"
      And admin panel loaded
      When on the main side menu choose "Users" item
      And on the expanded Users menu choose "Players" item
      Then url is "http://test-app.d6.dev.devcaz.com/user/player/admin"
      And players page loaded

    Scenario: Players sorting test
      Given user authorized in the admin panel
      And admin panel loaded
      And user went to the Players page
      And players page loaded
      When on Players table click on the "Registration date" column name
      Then url is "http://test-app.d6.dev.devcaz.com/user/player/admin?PlayerSearch_sort=registration_time"
      And sorting by column is correct

