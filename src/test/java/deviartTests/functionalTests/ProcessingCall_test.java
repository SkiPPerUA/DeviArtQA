package deviartTests.functionalTests;

import deviartTests.BaseTest;
import deviartTests.apiTests.LeadTest;
import org.deviartqa.blocks.filters.ModalWindow;
import org.deviartqa.helper.DataHelper;
import org.deviartqa.pages.main.WelcomePage;
import org.deviartqa.pages.shipping.ProcessingPage;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.SQLException;

@Test
public class ProcessingCall_test extends BaseTest {

    LeadTest leadTest = new LeadTest();
    WelcomePage welcomePage = new WelcomePage();
    ProcessingPage processingPage = new ProcessingPage();
    public String call_lead_id = "";
    public String call_id = "";

    public void approve_call() {
        processingPage
                .setSendTime(DataHelper.getTime("yyyy-MM-dd",1))
                .setZipcode("03111",3)
                .setStreet("fdsfsdfs")
                .setHouse("sdfds")
                .setAge(3)
                .setSex(false)
                .setComment_order("coment")
                .setCallResult()
                        .callProcess(ModalWindow.CallResult.approve,
                        ModalWindow.ResultAdditional.busy_ThrownOff,
                        "commen",
                        ModalWindow.OperatorStatus.stopProcessing)
                        .save(true);
    }

    public void busy_call() {
        processingPage
                .setCallResult()
                .callProcess(ModalWindow.CallResult.busy,
                        ModalWindow.ResultAdditional.busy_NoAnswer,
                        "commen",
                        ModalWindow.OperatorStatus.stopProcessing)
                .save(true);
    }

    @Test(enabled = false)
    public void busy_call_selectTime(String time) {
        processingPage
                .setCallResult()
                .callProcess_busyWithTime(time,"comment new time", ModalWindow.OperatorStatus.stopProcessing)
                .save(true);
    }

    public void reject_call() {
        processingPage
                .setCallResult()
                .callProcess(ModalWindow.CallResult.reject,
                        ModalWindow.ResultAdditional.busy_ThrownOff,
                        "reject",
                        ModalWindow.OperatorStatus.stopProcessing)
                .save(true);
    }

    public void trash_call() {
        processingPage
                .setCallResult()
                .callProcess(ModalWindow.CallResult.trash,
                        ModalWindow.ResultAdditional.trash_DuplicateOrder,
                        "trash",
                        ModalWindow.OperatorStatus.stopProcessing)
                .save(true);
    }

    public void techProblem_call() {
        processingPage
                .setCallResult()
                .callProcess(ModalWindow.CallResult.techProblem,
                        ModalWindow.ResultAdditional.trash_DuplicateOrder,
                        "techProblem",
                        ModalWindow.OperatorStatus.stopProcessing)
                .save(true);
    }

    @BeforeTest
    void auth() throws SQLException, InterruptedException {
        for (int i = 0; i<10; i++) {
            leadTest.create_lead_positive();
        }
        welcomePage.open().readyPage();
        welcomePage.header.changeUser(25559);
    }

    @BeforeMethod
    void startProcc(){
        processingPage.startProcessing();
        call_lead_id = processingPage.getCallInfo(ProcessingPage.CallParameters.lead_id);
        call_id = processingPage.getCallInfo(ProcessingPage.CallParameters.call_id);
    }
}
