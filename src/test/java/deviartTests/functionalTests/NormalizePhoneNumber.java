package deviartTests.functionalTests;

import deviartTests.BaseTest;
import org.deviartqa.api.LeadsAPI;
import org.deviartqa.helper.DataHelper;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

@Test
public class NormalizePhoneNumber extends BaseTest {

    String phone = "3123456789";
    String code = "39";
    String country = "IT";
    String lead_id;

    public void trash(){
        StringBuilder stringBuilder = new StringBuilder(phone);
        stringBuilder.replace(4,stringBuilder.length(),"");
        getDB().update("DELETE FROM terraleads.lead WHERE phone = '"+stringBuilder+"'");
        createLead(stringBuilder);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ResultSet res = getDB().select("SELECT * FROM terraleads.lead WHERE id = "+lead_id);
        try {
            res.next();
            Assert.assertEquals(res.getString("status"),"trash");
            Assert.assertEquals(res.getString("type"),"wrong_input_data");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAllNotNumberSymbol(){
        //удалить всё, что не цифра и не ведущий «+»
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("+=-(");
        stringBuilder.append(code);
        stringBuilder.append(")");
        char[] phoneChar = phone.toCharArray();
        for (int i = 0; i<phoneChar.length; i++){
            stringBuilder.append(phoneChar[i]);
            stringBuilder.append("-");
        }
        createLead(stringBuilder);
        checkBDdata(lead_id);
    }

    public void withoutCode(){
        createLead(new StringBuilder(phone));
        checkBDdata(lead_id);
    }

    @Test(priority = 1)
    public void changeNotNumberSymbol(){
        //привести к константе:
        //все латинские «O/o» → цифра «0»
        //любые экзотические или «арабские» цифры → обычные 0–9
        //убрать любые префиксы tel:, callto:, phone:// и тд
        //удалить всё, что не цифра и не ведущий «+»
        StringBuilder stringBuilder = new StringBuilder();
        String phoneInBD;
        String code = "39";
        country = "IT";
        String phone = "31o3O56789";
        stringBuilder.append("phone://++");
        stringBuilder.append(code);
        stringBuilder.append(phone);
        createLead(stringBuilder);
        ResultSet res = getDB().select("SELECT * FROM terraleads.lead WHERE id = "+lead_id);
        try {
            res.next();
            phoneInBD = res.getString("phone_std_format");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(phoneInBD,"+".concat(code).concat("3103056789"));
    }

    public void deleteNumberBeforeCode(){
        //legacy-фиксы
        //у каждой страны есть набор правил: «если длина == N и номер начинается с X → убрать/заменить X»
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("4235");
        stringBuilder.append(code);
        stringBuilder.append(phone);
        createLead(stringBuilder);
        checkBDdata(lead_id);
    }

    public void change00toPlus(){
        //интернациональный префикс 00 → +
        //если номер сейчас "00<код страны>…" → заменяем ведущие "00" на "+".
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("00");
        stringBuilder.append(code);
        stringBuilder.append(phone);
        createLead(stringBuilder);
        checkBDdata(lead_id);
    }

    public void addPlusBeforeCode(){
        //добавить пропущенный +
        //если номер не начинается с "+", но первые цифры совпадают с кодом страны → ставим "+" в начало
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(code);
        stringBuilder.append(phone);
        createLead(stringBuilder);
        checkBDdata(lead_id);
    }

    public void deleteDoubleCode(){
        //устранить дубли
        //если у нас 2 плюса или 2 кода страны
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("+");
        stringBuilder.append(code);
        stringBuilder.append("+");
        stringBuilder.append(code);
        stringBuilder.append(phone);
        createLead(stringBuilder);
        checkBDdata(lead_id);
    }

    public void addZeroForITandGR(){
        //транк-ноль (только для it и gr)
        //если у нас номер страны, то оставляем 0 после кода страны, но делаем попытку валидации и без нолика.
        //если валидны оба - приоритет варианту с "0" после кода страны
        if (country.equals("IT") || country.equals("RG")) {
            StringBuilder newPhone = new StringBuilder(phone);
            newPhone.substring(1,newPhone.length());
            newPhone.replace(0,1,"0");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(code);
            stringBuilder.append(newPhone);
            createLead(stringBuilder);
            checkBDdata(lead_id);

                //телефон с первым 0 валиден - значить он должен быть присвоен юзеру
                stringBuilder = new StringBuilder();
                stringBuilder.append(code);
                newPhone = new StringBuilder(phone);
                newPhone.substring(1,newPhone.length());
                stringBuilder.append(newPhone);
                createLead(stringBuilder);
                String phoneInBD;
                ResultSet res = getDB().select("SELECT * FROM terraleads.lead WHERE id = "+lead_id);
                try {
                    res.next();
                    phoneInBD = res.getString("phone_std_format");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Assert.assertEquals(phoneInBD,"+1".concat(code).concat("0").concat(phone));
        }
    }

    public void substringNumberMoreThanRange(){
        //поверка длины, считаем цифры после кода страны:
        //если больше ожидаемего диапазона для этой страны (должно быть в либе), пытаемся сохранить первую часть номера (без последних цифр)
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("+");
        stringBuilder.append(code);
        stringBuilder.append(phone);
        stringBuilder.append("41313123");
        createLead(stringBuilder);
        checkBDdata(lead_id);
    }

    private void createLead(StringBuilder phoneToCreate){
        ResultSet res = getDB().select("SELECT id FROM terraleads.offer where country_id = (SELECT id FROM terraleads.country WHERE country_code = '"+country+"' and status = 'active' and access_type = 'public')");
        try {
            if (res.next()){
                logger.info("Номер использован - ["+phoneToCreate+"]");
                String phoneInBD;
                int user_Id = 25696;
                LeadsAPI api = new LeadsAPI();
                api.createLead("{\n" +
                        "\"user_id\": "+user_Id+",\n" +
                        "    \"data\":{\n" +
                        "        \"offer_id\": \""+res.getInt("id")+"\",\n" +
                        "        \"name\": \"test"+ DataHelper.getUuid()+"\",\n" +
                        "        \"country\": \""+country+"\",\n" +
                        "        \"campaign\": \"38\",\n" +
                        "        \"phone\": \""+phoneToCreate+"\"\n" +
                        "        }\n" +
                        "}\n",user_Id);
                lead_id = new JSONObject(api.getResponse()).getJSONObject("data").getString("id");
                res = getDB().select("SELECT * FROM terraleads.lead WHERE id = "+lead_id);
                try {
                    res.next();
                    phoneInBD = res.getString("phone");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Assert.assertEquals(phoneInBD,phoneToCreate.toString());
            }else {
                Assert.fail("Оффер для страны {"+country+"} - НЕ найден");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void checkBDdata(String leadId){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String phoneInBD;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(code);
        stringBuilder.append(phone);
        ResultSet res = getDB().select("SELECT * FROM terraleads.lead WHERE id = "+leadId);
        try {
            res.next();
            phoneInBD = res.getString("phone_std_format");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(phoneInBD,stringBuilder.toString());
    }

    @BeforeTest
    public void start(){
        getDB().update("UPDATE terraleads.offer set country_id = (SELECT id FROM terraleads.country WHERE country_code = '"+country+"') where id = 10622");
    }
}
