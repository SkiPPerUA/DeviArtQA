package deviartTests.functionalTests;

import deviartTests.BaseTest;
import deviartTests.apiTests.LeadTest;
import org.deviartqa.blocks.filters.ModalWindow;
import org.deviartqa.pages.main.WelcomePage;
import org.deviartqa.pages.shipping.ProcessingPage;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.SQLException;

@Test
public class ProcessingCall_test extends BaseTest {

    LeadTest leadTest = new LeadTest();
    WelcomePage welcomePage = new WelcomePage();
    ProcessingPage processingPage = new ProcessingPage();
    String lead_id = "";

    public void approve_call() throws SQLException, InterruptedException {
//        leadTest.create_lead_positive();
//        lead_id = new JSONObject(leadTest.leads.getResponse()).getJSONObject("data").getString("id");
        processingPage.startProcessing();

        String call_lead_id = processingPage.getCallInfo(ProcessingPage.CallParameters.lead_id);

        processingPage
                .setSendTime("2025-11-11")
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
                        ModalWindow.OperatorStatus.continueProcessing)
                        .save(true);

        Thread.sleep(5000);


    }

    public void busy_call() throws SQLException, InterruptedException {
        processingPage.startProcessing();

        String call_lead_id = processingPage.getCallInfo(ProcessingPage.CallParameters.lead_id);

        processingPage
                .setCallResult()
                .callProcess(ModalWindow.CallResult.busy,
                        ModalWindow.ResultAdditional.busy_ThrownOff,
                        "commen",
                        ModalWindow.OperatorStatus.continueProcessing)
                .save(true);

        Thread.sleep(5000);


    }

    public void reject_call() throws SQLException, InterruptedException {
        processingPage.startProcessing();

        String call_lead_id = processingPage.getCallInfo(ProcessingPage.CallParameters.lead_id);

        processingPage
                .setCallResult()
                .callProcess(ModalWindow.CallResult.reject,
                        ModalWindow.ResultAdditional.busy_ThrownOff,
                        "reject",
                        ModalWindow.OperatorStatus.continueProcessing)
                .save(true);
    }

    public void trash_call() throws SQLException, InterruptedException {
        processingPage.startProcessing();

        String call_lead_id = processingPage.getCallInfo(ProcessingPage.CallParameters.lead_id);

        processingPage
                .setCallResult()
                .callProcess(ModalWindow.CallResult.trash,
                        ModalWindow.ResultAdditional.trash_DuplicateOrder,
                        "trash",
                        ModalWindow.OperatorStatus.continueProcessing)
                .save(true);
    }

    public void techProblem_call() throws SQLException, InterruptedException {
        processingPage.startProcessing();

        String call_lead_id = processingPage.getCallInfo(ProcessingPage.CallParameters.lead_id);

        processingPage
                .setCallResult()
                .callProcess(ModalWindow.CallResult.techProblem,
                        ModalWindow.ResultAdditional.trash_DuplicateOrder,
                        "techProblem",
                        ModalWindow.OperatorStatus.continueProcessing)
                .save(true);
    }

    @BeforeTest
    void auth(){
        welcomePage.open().readyPage();
        welcomePage.header.changeUser(25559);
    }
}
