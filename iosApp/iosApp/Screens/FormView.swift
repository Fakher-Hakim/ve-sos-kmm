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
                    SectionTitleComponent(title: "Autorisation de prélévement permanent")
                    Spacer()
                        .frame(height: 24.0)
                    VStack {
                        Toggle("Oui, en tant que parrain ou marrain SOS, Je souhaite participer à la prise en charge des enfants sans soutien familiale pour leur offrir un foyer chaleureux, une education de qualité et un avenir meilleur.", isOn: $isConsent)
                            .padding(.horizontal)
                            .tint(/*@START_MENU_TOKEN@*/.blue/*@END_MENU_TOKEN@*/)
                        
                        Spacer()
                            .frame(height: 24.0)
                        
                        HStack{
                            TextField("En Donnat", text: $amount)
                                .padding(.horizontal)
                                .keyboardType(/*@START_MENU_TOKEN@*/.numberPad/*@END_MENU_TOKEN@*/)
                                .background(Color(red: 0.89, green: 0.745, blue: 0.776, opacity: 0.38))
                            Spacer()
                                .frame(width: 24.0)
                            TextField("En lettre", text: $amountLettre)
                                .padding(.horizontal)
                                .background(Color(red: 0.89, green: 0.745, blue: 0.776, opacity: 0.38))
                        }
                        
                        Spacer()
                            .frame(height: 24.0)
                        
                        Text("Je souhaite parrainer parrainer des enfants au village de")
                        
                        Picker("Village", selection: $selectedVillage) {
                            ForEach(villages, id: \.self) {
                                Text($0)
                            }
                        }
                        .pickerStyle(.wheel)
                    }
                    .padding(.horizontal)
                }
                .listSectionSeparator(/*@START_MENU_TOKEN@*/.hidden/*@END_MENU_TOKEN@*/)
                
                Group {
                    SectionTitleComponent(title: "Mes Coordonnées")
                    VStack {
                        Spacer()
                            .frame(height: 24.0)
                        
                        HStack {
                            RadioButton(text: "Personne Physique", ifVariable: isPerson, onTapToActive: {
                                isPerson = true
                            }, onTapToInactive: {
                                isPerson = false
                            })
                            Spacer()
                            RadioButton(text: "Société", ifVariable: !isPerson, onTapToActive: {
                                isPerson = false
                            }, onTapToInactive: {
                                isPerson = true
                            })
                        }
                        
                        Spacer()
                            .frame(height: 24.0)
                        
                        if (isPerson) {
                            VStack {
                                TextField("Nom et prénom", text: $name)
                                    .padding(.horizontal)
                                    .background(Color(red: 0.89, green: 0.745, blue: 0.776, opacity: 0.38))
                                
                                Spacer()
                                    .frame(height: 24.0)
                                
                                TextField("Adresse", text: $address)
                                    .padding(.horizontal)
                                    .background(Color(red: 0.89, green: 0.745, blue: 0.776, opacity: 0.38))
                            }
                        } else {
                            
                            VStack {
                                TextField("Raison Sociale", text: $raisonSociale)
                                    .padding(.horizontal)
                                    .background(Color(red: 0.89, green: 0.745, blue: 0.776, opacity: 0.38))
                                
                                Spacer()
                                    .frame(height: 24.0)
                                
                                TextField("Matricule Fiscale", text: $matriculeFiscale)
                                    .padding(.horizontal)
                                    .background(Color(red: 0.89, green: 0.745, blue: 0.776, opacity: 0.38))
                                
                                Spacer()
                                    .frame(height: 24.0)
                                
                                TextField("Premier Responsable", text: $premierResponsable)
                                    .padding(.horizontal)
                                    .background(Color(red: 0.89, green: 0.745, blue: 0.776, opacity: 0.38))
                            }
                        }
                        
                        Spacer()
                            .frame(height: 24.0)
                        
                        TextField("Email", text: $email)
                            .padding(.horizontal)
                            .keyboardType(/*@START_MENU_TOKEN@*/.emailAddress/*@END_MENU_TOKEN@*/)
                            .background(Color(red: 0.89, green: 0.745, blue: 0.776, opacity: 0.38))
                        
                        Spacer()
                            .frame(height: 24.0)
                        
                        TextField("Téléphone", text: $phone)
                            .padding(.horizontal)
                            .keyboardType(/*@START_MENU_TOKEN@*/.phonePad/*@END_MENU_TOKEN@*/)
                            .background(Color(red: 0.89, green: 0.745, blue: 0.776, opacity: 0.38))
                    }
                    .padding(.horizontal)
                }
                .listSectionSeparator(.hidden)
                
                Group {
                    SectionTitleComponent(title: "Mes Coordonnées bancaire")
                    VStack {
                        Spacer()
                            .frame(height: 24.0)
                        HStack{
                            TextField("Banque", text: $bank)
                                .padding(.horizontal)
                                .background(Color(red: 0.89, green: 0.745, blue: 0.776, opacity: 0.38))
                            
                            Spacer()
                                .frame(width: 24.0)
                            
                            TextField("Agence", text: $agence)
                                .padding(.horizontal)
                                .background(Color(red: 0.89, green: 0.745, blue: 0.776, opacity: 0.38))
                        }
                        Spacer()
                            .frame(height: 24.0)
                        
                        TextField("RIB", text: $rib)
                            .padding(.horizontal)
                            .keyboardType(/*@START_MENU_TOKEN@*/.numberPad/*@END_MENU_TOKEN@*/)
                            .background(Color(red: 0.89, green: 0.745, blue: 0.776, opacity: 0.38))
                        
                        Spacer()
                            .frame(height: 24.0)
                        
                        Text("Premier mois de prélévement")
                        Picker("Village", selection: $selectedMonth) {
                            ForEach(months, id: \.self) {
                                Text($0)
                            }
                        }
                        .pickerStyle(.wheel)
                        
                        TextField("Fait A", text: $faitA)
                            .padding(.horizontal)
                            .background(Color(red: 0.89, green: 0.745, blue: 0.776, opacity: 0.38))
                    }
                    .padding(.horizontal)
                }
                .listSectionSeparator(.hidden)
            }
            .navigationBarTitleDisplayMode(.large)
            .toolbar {
                ToolbarItem(placement: .principal) {
                    VStack {
                        Text("VE SOS").font(.headline)
                    }
                }
                
                ToolbarItem(placement: .primaryAction) {
                    if(signature == nil) {
                        NavigationLink("Sign", destination: SignatureView(availableTabs: [.draw],
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
                                signatureBase64: signature?.jpegData(compressionQuality: 0.75)?.base64EncodedString() ?? ""
                            )
                            
                            if (isValid()) {
                                showingAlert = false
                                
                                let mailBody = DataProvider().getMailBodyHtml(formDataModel: data)
                                print("\(mailBody)")
                                
                                let emailComposer = SendEmailViewController()
                                emailComposer.sendEmail(body: mailBody)
                            } else {
                                showingAlert = true
                            }
                            
                        } label: {
                            Text("Submit")
                        }.alert("Veuillez remplir tous les champs SVP.", isPresented: $showingAlert) {
                            Button("OK", role: .cancel) { }
                        }
                    }
                }
            }
        }
    }
    
    private func isValid() -> Bool {
        if (!isConsent || faitA.isEmpty || amount.isEmpty || amountLettre.isEmpty || bank.isEmpty || agence.isEmpty || rib.isEmpty || email.isEmpty || address.isEmpty) {
            return false
        }
        return true
    }
}
    
    struct ContentView_Previews: PreviewProvider {
        static var previews: some View {
            FormView()
        }
    }
