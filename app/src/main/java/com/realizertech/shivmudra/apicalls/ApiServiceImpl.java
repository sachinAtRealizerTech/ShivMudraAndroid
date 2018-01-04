package com.realizertech.shivmudra.apicalls;



import com.realizertech.shivmudra.model.ChangeContactNoInput;
import com.realizertech.shivmudra.model.ItemListInput;
import com.realizertech.shivmudra.model.MasterDataInputs;
import com.realizertech.shivmudra.model.OrderInput;
import com.realizertech.shivmudra.model.ReferFriendInput;
import com.realizertech.shivmudra.model.ReferralReminderInput;
import com.realizertech.shivmudra.model.User;
import com.realizertech.shivmudra.model.UserInputs;
import com.realizertech.shivmudra.model.ValidationRequest;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by anh on 02/Feb/2017.
 */

public interface ApiServiceImpl {

    @GET("api/Masters/RetrieveLocations")
    Call<ResponseBody> getSocietyList();

    @POST("api/Masters/GetItemsList")
    Call<ResponseBody> getItemsList(@Body ItemListInput itemListInput);

    @POST("api/Masters/GetMasterTexts")
    Call<ResponseBody> getMasterTexts(@Body MasterDataInputs masterDataInputs);

    @POST("api/Masters/GetLocationDeliverySlabs")
    Call<ResponseBody> getLocationDeliverySlabs(@Body UserInputs userInputs);

    @POST("api/user/register")
    Call<ResponseBody> registration(@Body User user);

    @POST("api/user/ValidateToken")
    Call<ResponseBody> validateToken(@Body ValidationRequest validationRequest);

    @POST("api/user/GetConsumerStatistics")
    Call<ResponseBody> getConsumerStatistics(@Body UserInputs userInputs);

    @POST("api/user/ReferFriend")
    Call<ResponseBody> referFriend(@Body ReferFriendInput referFriendInput);

    @POST("api/user/GetReferrals")
    Call<ResponseBody> getReferrals(@Body UserInputs userInputs);

    @POST("api/orders/GetLastOrder")
    Call<ResponseBody> orders(@Body UserInputs userInputs);

    @POST("api/orders/PlaceOrder")
    Call<ResponseBody> placeOrder(@Body OrderInput orderInput);

    @POST("api/orders/GetAvailableDiscounts")
    Call<ResponseBody> getAvailableDiscounts(@Body UserInputs userInputs);

    @POST("api/user/GetDiscountsReceived")
    Call<ResponseBody> getDiscountsReceived(@Body UserInputs userInputs);

    @POST("api/user/GetAllowedReferralCount")
    Call<ResponseBody> getAllowedReferralCount(@Body UserInputs userInputs);

    @POST("api/user/SendReferralReminder")
    Call<ResponseBody> sendReferralReminder(@Body ReferralReminderInput referralReminderInput);

    @POST("api/user/sendConsumerOTP")
    Call<ResponseBody> sendConsumerOTP(@Body UserInputs userInputs);


    @POST("api/user/updateContactNo")
    Call<ResponseBody> updateContactNo(@Body ChangeContactNoInput changeContactNoInput);

}
