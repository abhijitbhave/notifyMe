
import Utils.OTPConfirmation;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;


//Writing tests for otp validation as well as generation of Otp.
// At this point since sending messages is already testing not retesting the sendOTP method as it leverages the same methods tested previously.
//Not testing the UX pieces.

public class testOTPConfirmation {

    OTPConfirmation otpConfirmation = new OTPConfirmation();

    @Test
    public void testValidateOTP() {
        long generatedOTP = otpConfirmation.generateOtp();
        long invalidNumber = 123123123;
        assertTrue(otpConfirmation.validateOTP(generatedOTP, generatedOTP));
        assertFalse(otpConfirmation.validateOTP(invalidNumber , generatedOTP));
    }

    @Test
    public void testGenerateOTP() {
        Class classObject = OTPConfirmation.class;
        Method[] methods =  classObject.getMethods();
        // Using streams to iterate through the various class methods returned by the Reflections
        // function to identify the return type of the generateOtp method. We need to ensure that in future someone
        // doesn’t change the return type of the method to something other than Long.
        Arrays.stream(methods).forEach(method -> {
            if(method.getName().equals("generateOtp")){
                Class returnType = method.getReturnType();
                assertTrue(returnType.toString().equals("long"));
            }
        });

    }

}
