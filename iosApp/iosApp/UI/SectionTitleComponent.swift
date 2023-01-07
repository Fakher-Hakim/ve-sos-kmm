//
//  SectionTitleComponent.swift
//  iosApp
//
//  Created by Fakher Hakim on 07/12/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct SectionTitleComponent: View {
    
    let title: String
    
    var body: some View {
        ZStack {
            Text(title)
                .font(.title)
                .foregroundColor(/*@START_MENU_TOKEN@*/.white/*@END_MENU_TOKEN@*/)
                .multilineTextAlignment(.center)
                .lineLimit(2)
        }
        .frame(maxWidth:.infinity,alignment:.center)
        .background(Color.blue)
    }
}

struct SectionTitleComponent_Previews: PreviewProvider {
    static var previews: some View {
        SectionTitleComponent(title: "Title goes here")
    }
}
