//
//  SignatureView.swift
//  iosApp
//
//  Created by Fakher Hakim on 06/01/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct SignatureView: View {
    var body: some View {
       
        SignaturePadView()
            .padding(.horizontal)
            .frame(width: 350, height: 350, alignment: .center)
            .overlay(
                    RoundedRectangle(cornerRadius: 16)
                        .stroke(.blue, lineWidth: 2)
                )
        
        Spacer()
        
        Button {
            print("submit button clicked")
        } label: {
            Text("Submit")
        }

    }
}

struct SignatureView_Previews: PreviewProvider {
    static var previews: some View {
        SignatureView()
    }
}
