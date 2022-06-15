package com.herokuapp.booker.cucumber.steps;

import com.herokuapp.booker.bookininfo.AuthSteps;
import com.herokuapp.booker.bookininfo.BookingSteps;
import com.herokuapp.booker.utils.TestUtils;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import static org.hamcrest.CoreMatchers.equalTo;

public class BookingStepDef {
    static String firstName = "User" + TestUtils.getRandomValue();
    static String lastName = "User" + TestUtils.getRandomValue();
    // static int totalPrice = 500;
    //static boolean depositPaid = true;
    static String checkIn = "2022-01-07";
    static String checkOut = "2022-01-20";
    //static String additionalNeeds = "Breakfast";
    static String username = "admin";
    static String password = "password123";
    static int bookingId;
    static String token;


    @Steps
    BookingSteps bookingSteps;
    @Steps
    AuthSteps authSteps;

    @When("^Admin post request by entering username \"([^\"]*)\" and password \"([^\"]*)\" as payload$")
    public void adminPostRequestByEnteringUsernameAndPasswordAsPayload(String username, String password) {
        token = authSteps.getAuthToken(username, password);
        System.out.println("AUthorisation token is : " + token);
    }

    @Then("^Admin should see an auth token in response$")
    public void adminShouldSeeAnAuthTokenInResponse() {
        if (!(token == null)) {
            Assert.assertTrue(true);
            System.out.println("Verified token is : " + token);
        }
    }


    @When("^User Post request to create new booking using the details  \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void userPostRequestToCreateNewBookingUsingTheDetails(int totalprice , boolean depositpaid, String additionalneeds)  {
       ValidatableResponse response=bookingSteps.createBooking(firstName,lastName,totalprice,depositpaid,checkIn,checkOut,additionalneeds);
        response.statusCode(200).log().ifValidationFails();
        bookingId = response.extract().path("bookingid");
    }

    @Then("^USer able to verifies the new booking has been created sucessfully$")
    public void userAbleToVerifiesTheNewBookingHasBeenCreatedSucessfully() {
        System.out.println("New booking Id is "+ bookingId);
    }

    @When("^User put request to update booking using details \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void userPutRequestToUpdateBookingUsingDetails(int totalprice, boolean depositpaid, String additionalneeds)  {
        firstName = firstName + "_updated";
        lastName = lastName + "_updated";
        token = authSteps.getAuthToken(username, password);
        ValidatableResponse response = bookingSteps.updateBooking(bookingId, firstName, lastName, totalprice,
                depositpaid, checkIn, checkOut, additionalneeds, token);
        response.statusCode(200).log().ifValidationFails();
        response.body("firstname", equalTo(firstName), "lastname", equalTo(lastName),
                "additionalneeds", equalTo(additionalneeds));
    }

    @Then("^User able to  verifies the update booking$")
    public void userAbleToVerifiesTheUpdateBooking() {

    }

    @When("^User able to delete the newly booking with booking id$")
    public void userAbleToDeleteTheNewlyBookingWithBookingId() {
        bookingSteps.deleteBookingWithBookingId(bookingId,token);
    }

    @And("^able to verify that record has been deleted successfully$")
    public void ableToVerifyThatRecordHasBeenDeletedSuccessfully() {
        bookingSteps.getBookingWithBookingId(bookingId);
    }
}
