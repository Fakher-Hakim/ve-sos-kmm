//
//  SendEmailViewController.swift
//  VE SOS
//
//  Created by Fakher Hakim on 06/01/2023.
//

import MessageUI
import UIKit

class SendEmailViewController: UIViewController, MFMailComposeViewControllerDelegate {
    
    let NEW_LINE = "\n"
    
    func sendEmail(data: FormData) {
        
        let recipientEmail = "parrainage.enfants@sos-tunisie.org"
        let subject = "SOS-VE formulaire"
        let body = "Merci de ne pas changer le contenu de ce mail\n"
        
        + "----\n" +
        
        "Banque: " + data.bank + NEW_LINE +
        "Agence: " + data.agence + NEW_LINE +
        "RIB: " + data.rib + NEW_LINE +
        "Mois de prelevement: ${monthSpinner.selectedItem}" + NEW_LINE +
        "Mois de prelevement: 2023"  + data.month + NEW_LINE
        "Annee de prelevement: 2023"  + NEW_LINE +
        
        "----\n" +
        
        "Montant: " + data.amount + NEW_LINE +
        "En lettre: " + data.amountLettre + NEW_LINE +
        "Village: " + data.village
        
        print(body)
        // Show default mail composer
        if MFMailComposeViewController.canSendMail() {
            let mail = MFMailComposeViewController()
            mail.mailComposeDelegate = self
            mail.setToRecipients([recipientEmail])
            mail.setSubject(subject)
            mail.setMessageBody(body, isHTML: false)
            
            let imageData: Data = (data.signature ?? UIImage()).pngData()!
            mail.addAttachmentData(imageData as Data, mimeType: "image/png", fileName: "sos_ve_signature.png")
            
            present(mail, animated: true, completion: nil)
            
            // Show third party email composer if default Mail app is not present
        } else if let emailUrl = createEmailUrl(to: recipientEmail, subject: subject, body: body) {
            UIApplication.shared.open(emailUrl)
        }
    }
    
    private func createEmailUrl(to: String, subject: String, body: String) -> URL? {
        let subjectEncoded = subject.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
        let bodyEncoded = body.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)!
        
        let gmailUrl = URL(string: "googlegmail://co?to=\(to)&subject=\(subjectEncoded)&body=\(bodyEncoded)")
        let outlookUrl = URL(string: "ms-outlook://compose?to=\(to)&subject=\(subjectEncoded)&body=\(bodyEncoded)")
        let yahooMail = URL(string: "ymail://mail/compose?to=\(to)&subject=\(subjectEncoded)&body=\(bodyEncoded)")
        let sparkUrl = URL(string: "readdle-spark://compose?recipient=\(to)&subject=\(subjectEncoded)&body=\(bodyEncoded)")
        let defaultUrl = URL(string: "mailto:\(to)?subject=\(subjectEncoded)&body=\(bodyEncoded)")
        
        if let gmailUrl = gmailUrl, UIApplication.shared.canOpenURL(gmailUrl) {
            return gmailUrl
        } else if let outlookUrl = outlookUrl, UIApplication.shared.canOpenURL(outlookUrl) {
            return outlookUrl
        } else if let yahooMail = yahooMail, UIApplication.shared.canOpenURL(yahooMail) {
            return yahooMail
        } else if let sparkUrl = sparkUrl, UIApplication.shared.canOpenURL(sparkUrl) {
            return sparkUrl
        }
        
        return defaultUrl
    }
    
    func mailComposeController(_ controller: MFMailComposeViewController, didFinishWith result: MFMailComposeResult, error: Error?) {
        controller.dismiss(animated: true)
    }
}
