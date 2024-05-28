package framework.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.testng.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public  class AssertionModel {

    private String methodName;
    private Boolean status = true;
    private String errorMessage;
    private String media;

    public void verify(){
        if(!getStatus())
        {
            Assert.fail(getErrorMessage());
        }
    }
    public String currentDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("HH_mm_ss");
        Date date = new Date();
        String dateName = dateFormat.format(date);
        return dateName;
    }

}
