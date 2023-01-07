//
//  RadioButton.swift
//  iosApp
//
//  Created by Fakher Hakim on 08/12/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct RadioButton: View {
    
    let text: String
    let ifVariable: Bool    //the variable that determines if its checked
    let onTapToActive: ()-> Void//action when taped to activate
    let onTapToInactive: ()-> Void //action when taped to inactivate
    
    var body: some View {
        Group{
            if ifVariable {
                HStack {
                    ZStack{
                        Circle()
                            .fill(Color.blue)
                            .frame(width: 20, height: 20)
                        Circle()
                            .fill(Color.white)
                            .frame(width: 8, height: 8)
                    }
                    Spacer()
                        .frame(width:12)
                    Text(text)
                }.onTapGesture {self.onTapToInactive()}
            } else {
                HStack {
                Circle()
                    .fill(Color.white)
                    .frame(width: 20, height: 20)
                    .overlay(Circle().stroke(Color.gray, lineWidth: 1))
                    
                    Spacer()
                        .frame(width:12)
                    Text(text)
                }.onTapGesture {self.onTapToActive()}
            }
        }
    }
}

struct RadioButton_Previews: PreviewProvider {
    static var previews: some View {
        RadioButton(text: "Option", ifVariable: true) {
            
        } onTapToInactive: {
            
        }
        
    }
}
