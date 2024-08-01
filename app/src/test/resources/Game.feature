Feature: Game

  Scenario: Empty League
    Given the league has no players
    When I print the league
    Then I should see "No players yet"

  Scenario: Game with two players where one beats the other and becomes the winnder
    Given the league has no players
    When I add a player to the league called Alice
    Then the league has a player called Alice
    When I add a player to the league called Bob
    Then the league has a player called Bob
    When Bob wins against Alice 
    Then the league winner is Bob

