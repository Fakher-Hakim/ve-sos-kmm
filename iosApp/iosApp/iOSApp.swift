import SwiftUI

@main
struct iOSApp: App {
    
    init() {        
        for family: String in UIFont.familyNames
        {
            print(family)
            for names: String in UIFont.fontNames(forFamilyName: family)
            {
                print("== \(names)")
            }
        }
    }
    
	var body: some Scene {
        
		WindowGroup {
			FormView()
		}
	}
}
