package com.espire.email.main;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.espire.campaign.camp.service.CampaignService;
import com.espire.domain.Campaign;
import com.espire.domain.Communication;
import com.espire.domain.CommunicationTracker;
import com.espire.domain.Edm;
import com.espire.domain.Status;
import com.espire.domain.User;
import com.espire.email.job.BatchEmailJob;
import com.espire.email.job.EmailJob;

public class EmailJobFactory extends JobFactory {

	final static Logger log = Logger.getLogger(EmailJobFactory.class);	

	private CampaignService campaignService;

	public EmailJobFactory( CampaignService campaignService){

		this.campaignService = campaignService;
	}

	public BatchEmailJob createEmailJobs(User loginUser,Edm edm,boolean trial){
		BatchEmailJob batchJob = new BatchEmailJob();
		batchJob.setBatchJobId(""+new Date().getTime());
		batchJob.setEmailJobList(new ArrayList<EmailJob>());
		
		Campaign campaign = edm.getCampaign();
		if(trial){
			campaign.setStatus(campaignService.getStatusByDesc("TEST"));
		}else{
			campaign.setStatus(campaignService.getStatusByDesc("EXECUTED"));
		}

		for(Communication comm : edm.getCampaign().getCommunicationList()){

			if(comm.isTrial().equals(trial)){ // parse only those contacts whose trial mode matches the method trial mode.
				CommunicationTracker ct = new CommunicationTracker();
				Status status = null;
				if(trial){
					status = campaignService.getStatusByDesc("TEST"); 
				}else{
					status = campaignService.getStatusByDesc("CREATE");  
				}
				ct.setStatus(status);
				ct.setCommunication(comm);
				ct.setEdm(edm);
				ct.setUser(loginUser);
				 /* COMMUNICATION TRACKER ID GENERATED AT THIS STEP WILL BE USED FOR CREATING TRACKING IDS*/
				campaignService.createCommTracker(ct);
				setEmailTemplate(edm.getEdmHtml());
				EmailJob emailJob= new EmailJob(ct.getCommunicationTrackerID(),comm.getContact().getEmail(),comm.getContact().getContactName());
				emailJob.setSubject(edm.getSubject());
				parseEmailBody(emailJob);
				batchJob.getEmailJobList().add(emailJob);
				
			}

		}
		return batchJob;
	}

}
