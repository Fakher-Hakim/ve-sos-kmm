import SwiftUI
import SwiftUIDigitalSignature
import shared

struct FormView: View {
    
    @State private var signature: UIImage? = nil
    
    @State private var isConsent = false
    @State private var amount = ""
    @State private var amountLettre = ""
    @State private var name = ""
    @State private var profession = ""
    @State private var raisonSociale = ""
    @State private var matriculeFiscale = ""
    @State private var premierResponsable = ""
    @State private var address = ""
    @State private var email = ""
    @State private var phone = ""
    @State private var bank = ""
    @State private var agence = ""
    @State private var rib = ""
    @State private var month = ""
    @State private var date = ""
    @State private var faitA = ""
    @State private var isPerson = true
    
    @State private var selectedVillage = "Mahres"
    let villages = ["Gammarth", "Siliana", "Mahres", "Akouda"]
    
    @State private var selectedMonth = "Juin"
    let months = ["Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"]
    
    let personType = ["Person", "Company"]
    
    @State private var showingAlert = false
    
    var body: some View {
        NavigationView {
            List {
                Group{
                    SectionTitleComponent(title: "Autorisation de prelevement permanent")
                        .listRowSeparator(.hidden)
                    
                    VStack {
                        Toggle("Oui, en tant que parrain ou marrain SOS, Je souhaite participer à la prise en charge des enfants sans soutien familiale pour leur offrir un foyer chaleureux, une education de qualite et un avenir meilleur.", isOn: $isConsent)
                            .padding(.horizontal)
                            .tint(/*@START_MENU_TOKEN@*/.blue/*@END_MENU_TOKEN@*/)
                            .font(.custom("Barlow-Bold", size: 12))
                        
                        HStack{
                            TextField("999 DT", text: $amount)
                                .keyboardType(/*@START_MENU_TOKEN@*/.numberPad/*@END_MENU_TOKEN@*/)
                                .padding(.horizontal, 4.0)
                                .padding(EdgeInsets(top: 0, leading: 4, bottom: 0, trailing: 4))
                                .background(Color(red: 0.89, green: 0.745, blue: 0.776, opacity: 0.38))
                                .cornerRadius(/*@START_MENU_TOKEN@*/16.0/*@END_MENU_TOKEN@*/)
                                .font(.custom("Orbitron-Regular", size: 20))
                            
                            Spacer()
                                .frame(width: 16)
                            
                            TextField("En lettre", text: $amountLettre)
                                .textFieldStyle(VETextFieldStyle())
                        }
                        .padding(.top, 24.0)
                        
                        Text("Je souhaite parrainer parrainer des enfants au village de")
                            .font(.custom("Barlow-Bold", size: 16))
                            .padding(.top, 24.0)
                        
                        Picker("Village", selection: $selectedVillage) {
                            ForEach(villages, id: \.self) {
                                Text($0)
                                    .font(.custom("Barlow-Bold", size: 14))
                            }
                        }
                        .pickerStyle(.wheel)
                    }
                    .padding(.horizontal)
                }
                .listSectionSeparator(/*@START_MENU_TOKEN@*/.hidden/*@END_MENU_TOKEN@*/)
                
                Group {
                    SectionTitleComponent(title: "Mes Coordonnees")
                        .listRowSeparator(.hidden)
                    
                    VStack {
                        HStack {
                            RadioButton(text: "Personne Physique", ifVariable: isPerson, onTapToActive: {
                                isPerson = true
                            }, onTapToInactive: {
                                isPerson = false
                            })
                            .font(.custom("Barlow-Bold", size: 14))
                            Spacer()
                            RadioButton(text: "Societe", ifVariable: !isPerson, onTapToActive: {
                                isPerson = false
                            }, onTapToInactive: {
                                isPerson = true
                            })
                            .font(.custom("Barlow-Bold", size: 14))
                        }
                        .padding(.vertical, 16.0)
                        
                        if (isPerson) {
                            VStack {
                                TextField("Nom et prenom", text: $name)
                                    .textFieldStyle(VETextFieldStyle())
                                
                                TextField("Adresse", text: $address)
                                    .textFieldStyle(VETextFieldStyle())
                            }
                        } else {
                            
                            VStack {
                                TextField("Raison Sociale", text: $raisonSociale)
                                    .textFieldStyle(VETextFieldStyle())
                                
                                TextField("Matricule Fiscale", text: $matriculeFiscale)
                                    .textFieldStyle(VETextFieldStyle())
                                
                                TextField("Premier Responsable", text: $premierResponsable)
                                    .textFieldStyle(VETextFieldStyle())
                            }
                        }
                        
                        TextField("Email", text: $email)
                            .keyboardType(/*@START_MENU_TOKEN@*/.emailAddress/*@END_MENU_TOKEN@*/)
                            .textFieldStyle(VETextFieldStyle())
                        
                        TextField("+216 111 111", text: $phone)
                            .keyboardType(/*@START_MENU_TOKEN@*/.phonePad/*@END_MENU_TOKEN@*/)
                            .padding(.horizontal, 4.0)
                            .padding(EdgeInsets(top: 0, leading: 4, bottom: 0, trailing: 4))
                            .background(Color(red: 0.89, green: 0.745, blue: 0.776, opacity: 0.38))
                            .cornerRadius(/*@START_MENU_TOKEN@*/16.0/*@END_MENU_TOKEN@*/)
                            .font(.custom("Orbitron-Regular", size: 20))
                    }
                    .padding(.horizontal)
                }
                .listSectionSeparator(.hidden)
                
                Group {
                    SectionTitleComponent(title: "Mes Coordonnees bancaire")
                        .listRowSeparator(.hidden)
                    
                    VStack {
                        
                        HStack{
                            TextField("Banque", text: $bank)
                                .textFieldStyle(VETextFieldStyle())
                            
                            TextField("Agence", text: $agence)
                                .textFieldStyle(VETextFieldStyle())
                        }
                        
                        TextField("RIB 1234567890000", text: $rib)
                            .keyboardType(/*@START_MENU_TOKEN@*/.numberPad/*@END_MENU_TOKEN@*/)
                            .padding(.horizontal, 4.0)
                            .padding(EdgeInsets(top: 0, leading: 4, bottom: 0, trailing: 4))
                            .background(Color(red: 0.89, green: 0.745, blue: 0.776, opacity: 0.38))
                            .cornerRadius(/*@START_MENU_TOKEN@*/16.0/*@END_MENU_TOKEN@*/)
                            .font(.custom("Orbitron-Regular", size: 20))
                        
                        Text("Premier mois de prelevement")
                            .font(.custom("Barlow-Bold", size: 18))
                            .padding(.top, 24.0)
                        
                        Picker("Village", selection: $selectedMonth) {
                            ForEach(months, id: \.self) {
                                Text($0)
                                    .font(.custom("Barlow-Bold", size: 16))
                            }
                        }
                        .pickerStyle(.wheel)
                        
                        TextField("Fait A...", text: $faitA)
                            .textFieldStyle(VETextFieldStyle())
                    }
                    .padding(.horizontal)
                }
                .listSectionSeparator(.hidden)
            }
            .navigationBarTitleDisplayMode(.large)
            .toolbar {
                ToolbarItem(placement: .principal) {
                    VStack {
                        Text("VE SOS")
                            .font(.headline)
                    }
                }
                
                ToolbarItem(placement: .primaryAction) {
                    if(signature == nil) {
                        NavigationLink("Signer", destination: SignatureView(availableTabs: [.draw],
                                                                          onSave: { image in
                            self.signature = image
                            
                        }, onCancel: {
                            
                        }))
                        if signature != nil {
                            Image(uiImage: signature!)
                        }
                    } else {
                        Button {
                            let data = FormDataModel(
                                isPerson: isPerson,
                                amount: amount,
                                amountLetter: amountLettre,
                                village: selectedVillage,
                                name: name,
                                address: address,
                                profession: profession,
                                email: email,
                                phone: phone,
                                raisonSocial: raisonSociale,
                                matricule: matriculeFiscale,
                                premierResponsable: premierResponsable,
                                bank: bank,
                                agence: agence,
                                rib: rib,
                                month: month,
                                faitA: faitA,
                                signatureBase64: signature?.jpegData(compressionQuality: 0.25)?.base64EncodedString() ?? ""
                            )
                            
                            if (isValid()) {
                                print("form valid, sending...")
                                showingAlert = false
                                
                                let mailBody = DataProvider().getMailBodyHtml(formDataModel: data)
                                print("\(mailBody)")
                                
                                let emailComposer = SendEmailViewController()
                                emailComposer.sendEmail(body: mailBody)
                            } else {
                                showingAlert = true
                            }
                            
                        } label: {
                            Text("Envoyer")
                        }.alert("Veuillez remplir tous les champs SVP.", isPresented: $showingAlert) {
                            Button("OK", role: .cancel) { }
                        }
                    }
                }
            }
        }
        .navigationViewStyle(StackNavigationViewStyle())
    }
    
    private func isValid() -> Bool {
        if (!isConsent || faitA.isEmpty || amount.isEmpty || amountLettre.isEmpty || bank.isEmpty || agence.isEmpty || rib.isEmpty || email.isEmpty || address.isEmpty) {
            return false
        }
        return true
    }
}

struct VETextFieldStyle: TextFieldStyle {
    func _body(configuration: TextField<Self._Label>) -> some View {
        configuration
            .font(.custom("Barlow-Regular", size: 20))
            .padding(.horizontal, 4.0)
            .padding(EdgeInsets(top: 0, leading: 4, bottom: 0, trailing: 4))
            .background(Color(red: 0.89, green: 0.745, blue: 0.776, opacity: 0.38))
            .cornerRadius(/*@START_MENU_TOKEN@*/16.0/*@END_MENU_TOKEN@*/)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        FormView()
    }
}
