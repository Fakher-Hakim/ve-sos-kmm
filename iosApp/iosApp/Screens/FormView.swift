import SwiftUI
import shared

struct FormView: View {
    
    @State private var isConsent = false
    @State private var amount = ""
    @State private var amountLettre = ""
    @State private var name = ""
    @State private var address = ""
    @State private var email = ""
    @State private var phone = ""
    @State private var bank = ""
    @State private var agence = ""
    @State private var rib = ""
    @State private var month = ""
    @State private var date = ""
    @State private var isPerson = true
    
    @State private var selectedStrength = "Gammarth"
    let strengths = ["Gammarth", "Siliana", "Mahres", "Akouda"]
    
    let personType = ["Person", "Company"]
    
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
                        
                        Picker("Village", selection: $selectedStrength) {
                            ForEach(strengths, id: \.self) {
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
                        
                        TextField("Nom et prénom", text: $name)
                            .padding(.horizontal)
                            .background(Color(red: 0.89, green: 0.745, blue: 0.776, opacity: 0.38))
                        
                        Spacer()
                            .frame(height: 24.0)
                        
                        TextField("Adresse", text: $address)
                            .padding(.horizontal)
                            .background(Color(red: 0.89, green: 0.745, blue: 0.776, opacity: 0.38))
                        
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
                        //TODO month picker.
                    }
                    .padding(.horizontal)
                }
                .listSectionSeparator(.hidden)
                
                Group {
                    SectionTitleComponent(title: "Signature")
                    VStack {
                        Spacer()
                            .frame(height: 24.0)
                        TextField("Fait à", text: $date)
                            .padding(.horizontal)
                            .background(Color(red: 0.89, green: 0.745, blue: 0.776, opacity: 0.38))
                        
                        
                
                    }
                    .padding(.horizontal)
                }
                .listSectionSeparator(/*@START_MENU_TOKEN@*/.hidden/*@END_MENU_TOKEN@*/)
            }
            .navigationBarTitleDisplayMode(.large)
            .toolbar {
                ToolbarItem(placement: .principal) {
                    VStack {
                        Text("VE SOS").font(.headline)
                    }
                }
                
                ToolbarItem(placement: .primaryAction) {
                    NavigationLink {
                        SignatureView()
                    } label: {
                        Text("Signer")
                    }

                }
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        FormView()
    }
}
