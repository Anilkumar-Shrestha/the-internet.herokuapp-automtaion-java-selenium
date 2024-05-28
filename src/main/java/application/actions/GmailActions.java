package application.actions;

import framework.driver.Waiter;
import framework.utility.EmailUtils;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.Optional;

import static framework.utility.loggerator.Logger.getLogger;

public class GmailActions {
//	public static void main(String args[]) throws Exception {
//		Message message = waitNewMailWithSubjectContains("editEmail", "editPassword","Welcome to Test Automation");
//		String subject = message.getSubject();
//		String body = GmailActions.getEmailBody(message);
//	}

	public static String getEmailBody(Message message) throws Exception {
		return EmailUtils.getMessageContent(message);
	}

	public static String getEmailSubject(Message message) throws MessagingException {
		return message.getSubject();
	}

	public static Address[] getEmailReceivers(Message message) throws MessagingException {
		return message.getRecipients(Message.RecipientType.TO);
	}

	public static Message waitNewMailWithSubjectContains(String userName, String password, String subject) throws MessagingException {
		getLogger().info("Waiting for email with subject `" +subject+ "` to be received");
		EmailUtils email = new EmailUtils(userName, password);
		Optional<Object> foundMessage = Optional.empty();;
		Message emailMessage ;
		int initialNumber = email.getNumberOfMessages();
		for (int i = 0; i < 6; i++) {
			int finalInitialNumber = initialNumber;
			 Boolean isFound= Waiter.waitWithoutDriver(() -> {
						try {
							getLogger().debug("get count");
							if (finalInitialNumber < email.getNumberOfMessages()) {
								Message[] messagesArray = email. getMessages(email.getNumberOfMessages()-finalInitialNumber);
								// checking subject match with all the latest inbox messages received to prevent message lost in between of multiple message received at same time
								return Arrays.stream(Arrays.stream(messagesArray).toArray())
										.anyMatch(msg -> {
											try {
												return getEmailSubject((Message) msg).contains(subject);
											} catch (MessagingException e) {
												e.printStackTrace();
											}
											return false;
										} );
							}
							return false;
						} catch (MessagingException e) {
							getLogger().error("Can not get count of messages.");
							return false;
						}
					},
					"Not increased count of messages");

			if (isFound) {
				Message[] messagesArray = email. getMessages(email.getNumberOfMessages()-initialNumber);
				// checking subject match with all the latest inbox messages received
				foundMessage = Arrays.stream(Arrays.stream(messagesArray).toArray())
						 .filter(msg -> {
							 try {
								 return getEmailSubject((Message) msg).contains(subject);
							 } catch (MessagingException e) {
								 e.printStackTrace();
							 }
							 return false;
						 } ).findFirst();
				break;
				}else initialNumber =email.getNumberOfMessages();
			}
		if(foundMessage.isPresent()){
			emailMessage = (Message) foundMessage.get();
			getLogger().info("email message with subject ` " +emailMessage.getSubject() + " ` found.");
			return (emailMessage);
		}
		throw new AssertionError("Message with subject `" +subject+ "` was not received. Waited until 120 seconds.");
	}

	public static Message waitNewMailWithSubjectAndReceiversContains(String userName, String password, String subject, String receiversEmail) throws MessagingException {
		return waitNewMailWithSubjectAndReceiversContainsAndGivenInitialCount(userName, password,subject,receiversEmail,666);
	}

	// added this method with `initialCountOfEmail` params for testing invitation in parallel execution
	public static Message waitNewMailWithSubjectAndReceiversContainsAndGivenInitialCount(String userName, String password, String subject, String receiversEmail, int initialCountOfEmail) throws MessagingException {
		getLogger().info("Waiting for email with subject `" +subject+ "` and receivers as `"+receiversEmail+"` to be received");
		EmailUtils email = new EmailUtils(userName, password);
		Optional<Object> foundMessage = Optional.empty();;
		Message emailMessage ;
		int initialNumber = initialCountOfEmail==0?email.getNumberOfMessages():initialCountOfEmail;
		System.out.println("debugg");
		for (int i = 0; i < 6; i++) {
			int finalInitialNumber = initialNumber;
			Boolean isFound= Waiter.waitWithoutDriver(() -> {
						try {
							getLogger().debug("get count");
							if (finalInitialNumber < email.getNumberOfMessages()) {
								Message[] messagesArray = email. getMessages(email.getNumberOfMessages()-finalInitialNumber);
								// checking subject match with all the latest inbox messages received to prevent message lost in between of multiple message received at same time
								return Arrays.stream(Arrays.stream(messagesArray).toArray())
										.anyMatch(msg -> {
											try {
												return getEmailSubject((Message) msg).contains(subject) && Arrays.stream(getEmailReceivers((Message) msg)).anyMatch(a -> {
															return a.toString().equals(receiversEmail);
														}
												);
											} catch (MessagingException e) {
												e.printStackTrace();
											}
											return false;
										} );
							}
							return false;
						} catch (MessagingException e) {
							getLogger().error("Can not get count of messages.");
							return false;
						}
					},
					"Not increased count of messages");

			if (isFound) {
				Message[] messagesArray = email. getMessages(email.getNumberOfMessages()-initialNumber);
				// checking subject match with all the latest inbox messages received
				foundMessage = Arrays.stream(Arrays.stream(messagesArray).toArray())
						.filter(msg -> {
							try {
								return getEmailSubject((Message) msg).contains(subject) && Arrays.stream(getEmailReceivers((Message) msg)).anyMatch(a -> {
											return a.toString().equals(receiversEmail);
										}
								);
							} catch (MessagingException e) {
								e.printStackTrace();
							}
							return false;
						} ).findFirst();
				break;
			}else initialNumber =email.getNumberOfMessages();
		}
		if(foundMessage.isPresent()){
			emailMessage = (Message) foundMessage.get();
			getLogger().info("email message with subject ` " +emailMessage.getSubject() +"` and receivers as `"+receiversEmail+"` to be received found.");
			return (emailMessage);
		}
		throw new AssertionError("Message with subject `" +subject+ "` and receivers as `"+receiversEmail+"` to be received was not received. Waited until 120 seconds.");
	}


}
