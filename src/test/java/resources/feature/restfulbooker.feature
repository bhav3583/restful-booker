Feature: Booking functionality
  Testing the booking functionality by performing end to end testing using CRUD operations

  @Regression
  Scenario: Admin should be able to generate a new auth token successfully by entering the
  valid username and password
    When Admin post request by entering username "admin" and password "password123" as payload
    Then Admin should see an auth token in response

  @Regression
  Scenario Outline: User should able to create new booking
    When User Post request to create new booking using the details  "<totalprice>" "<depositpaid>" "<additionalneeds>"
    Then USer able to verifies the new booking has been created sucessfully

    Examples:
      | totalprice | depositpaid | additionalneeds |
      | 250        | true        | Vegetarian Food |

  @Regression
  Scenario Outline: User should able to update bookings
    When User put request to update booking using details "<totalprice>" "<depositpaid>" "<additionalneeds>"
    Then User able to  verifies the update booking
    Examples:
      | totalprice | depositpaid | additionalneeds |
      | 300        | true        | Chinese Food    |

    @Regression
    Scenario:
      When User able to delete the newly booking with booking id
      And able to verify that record has been deleted successfully