package deviartTests.functionalTests;

import deviartTests.BaseTest;
import org.testng.annotations.Test;

import java.util.List;

@Test
public class testNorm extends BaseTest {

    NormalizePhoneNumber norm;

    public void allTest(){
        List<String> phone = List.of("6641234567","881234567","99123456","777123456","5512345678","51234567","612345678","612345678"
        ,"6912345678","912345678","201234567","3123456789","61234567","22123456","512345678","912345678","712345678","912345678","40123456");
        List<String> code = List.of("43","359","357","420","49","372","34","33","30","385","36","39","370","371","48","351","40","421","386");
        List<String> country = List.of("AT","BG","CY","CZ","DE","EE","ES","FR","GR","HR","HU","IT","LT","LV","PL","PT","RO","SK","SI");

        for (int i = 0; i < phone.size(); i++){
            logger.info("======= "+country.get(i)+" =======");
            norm = new NormalizePhoneNumber();
            norm.phone = phone.get(i);
            norm.code = code.get(i);
            norm.country = country.get(i);

            getDB().update("UPDATE terraleads.offer set country_id = (SELECT id FROM terraleads.country WHERE country_code = '"+country.get(i)+"') where id = 10622");
            try {
                norm.addPlusBeforeCode();
            }catch (AssertionError e){
                System.out.println(country.get(i)+ " бага");
            }
            try {
                norm.addZeroForITandGR();
            }catch (AssertionError e){
                System.out.println(country.get(i)+ " бага");
            }
            try {
                norm.change00toPlus();
            }catch (AssertionError e){
                System.out.println(country.get(i)+ " бага");
            }
            try {
                norm.deleteDoubleCode();
            }catch (AssertionError e){
                System.out.println(country.get(i)+ " бага");
            }
            try {
                norm.deleteAllNotNumberSymbol();
            }catch (AssertionError e){
                System.out.println(country.get(i)+ " бага");
            }
            try {
                norm.deleteNumberBeforeCode();
            }catch (AssertionError e){
                System.out.println(country.get(i)+ " бага");
            }
            try {
                norm.substringNumberMoreThanRange();
            }catch (AssertionError e){
                System.out.println(country.get(i)+ " бага???????");
            }
            try {
                norm.trash();
            }catch (AssertionError e){
                System.out.println(country.get(i)+ " бага");
            }

        }
    }
}
