//
//  MessageObject.h
//  UDApp
//
//  Created by Tushar Verma on 07/02/16.
//  Copyright © 2016 Underdark. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface MessageObject : NSObject{
    
    NSString *_sourceId;
    NSString *_destinationId;
    NSString *_messageType;
    NSString *_message;
}

@property (nonatomic, retain) NSString *sourceId;   
@property (nonatomic, retain) NSString *destinationId;
@property (nonatomic, retain) NSString *messageType;
@property (nonatomic, retain) NSString *message;

-(NSDictionary *)dictionary;

@end
