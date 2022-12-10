//
//  SignaturePadView.swift
//  iosApp
//
//  Created by Fakher Hakim on 10/12/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import SignaturePad

struct SignaturePadView: UIViewRepresentable {
        
    func makeUIView(context: Context) -> SignaturePad {
        return SignaturePad(frame: .zero)
    }
    
    func updateUIView(_ uiView: SignaturePad, context: Context) {
        print(uiView.self)
    }
}

struct SignaturePadView_Previews: PreviewProvider {
    static var previews: some View {
        SignaturePadView()
    }
}
