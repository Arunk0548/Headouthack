//
//  MessageObject.m
//  UDApp
//
//  Created by Tushar Verma on 07/02/16.
//  Copyright © 2016 Underdark. All rights reserved.
//

#import "MessageObject.h"

@implementation MessageObject

@synthesize sourceId = _sourceId;
@synthesize destinationId = _destinationId;
@synthesize messageType = _messageType;
@synthesize message = _message;

-(void) dealloc {
    self.sourceId = nil;
    self.destinationId = nil;
    self.messageType = nil;
    self.message = nil;
    
}

-(NSDictionary *)dictionary {
    return [NSDictionary dictionaryWithObjectsAndKeys:self.sourceId,@"sourceId", self.destinationId,  @"destinationId", self.messageType, @"messageType",  self.message, @"message", nil];
}

@end
