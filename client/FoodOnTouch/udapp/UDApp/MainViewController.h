//
//  MainViewController.h
//  UDApp
//
//  Created by Tushar Verma on 06/02/16.
//  Copyright © 2016 Underdark. All rights reserved.
//

#import <UIKit/UIKit.h>
@class Node;
#import "MessageObject.h"

@interface MainViewController : UIViewController<UITableViewDataSource, UITableViewDelegate>

@property (weak, nonatomic) IBOutlet UIView *sView;
@property (weak, nonatomic) IBOutlet UILabel *peersCountLabel;
@property (weak, nonatomic) IBOutlet UILabel *framesCountLabel;
@property (weak, nonatomic) IBOutlet UIButton *sendFramesButton;
@property (weak, nonatomic) IBOutlet UIImageView *dealImageView;
@property (weak, nonatomic) IBOutlet UILabel *headerLabel;
@property (weak, nonatomic) IBOutlet UITableView *menuTableView;
@property (weak, nonatomic) IBOutlet UIView *coffeeView;
@property (weak, nonatomic) IBOutlet UITableView *coffeeTableView;
@property (weak, nonatomic) IBOutlet UIButton *orderButton;
@property (weak, nonatomic) IBOutlet UILabel *quantityLabel;

@property (weak, nonatomic) IBOutlet UIButton *backBtn;

@property (weak, nonatomic) IBOutlet UIView *orderSummaryView;
@property (weak, nonatomic) IBOutlet UIImageView *seatImageView;
@property (weak, nonatomic) IBOutlet UIImageView *summaryView;
@property (weak, nonatomic) IBOutlet UIButton *confirmBtn;
@property (weak, nonatomic) IBOutlet UIButton *modifyBtn;


@property (weak, nonatomic) IBOutlet UIView *orderStatusView;

@property (weak, nonatomic) IBOutlet UIImageView *greenTeaImageView;
@property (weak, nonatomic) IBOutlet UIImageView *espressoImageView;
@property (weak, nonatomic) IBOutlet UIImageView *hotChocolateImageView;
@property (weak, nonatomic) IBOutlet UIImageView *cappuccinoImageView;


@property (weak, nonatomic) IBOutlet UIView *invoicePaymentView;
@property (weak, nonatomic) IBOutlet UIButton *paymentButton;


@property (nonatomic, readwrite) int currentViewTagValue;

@property (weak, nonatomic) IBOutlet UIView *paymentInitiated;
@property (weak, nonatomic) IBOutlet UIImageView *paymentInfoImageView;

@property (retain, nonatomic) Node *nodeObj;

@property (nonatomic, readwrite) int numberOfHotChocolateSelected;

@property (nonatomic, retain) NSMutableDictionary *orderDict;

@property (nonatomic, retain) MessageObject *msgObj;

@property BOOL anounceValue;

- (IBAction)orderConfirm:(id)sender;
- (IBAction)actionBack:(id)sender;
- (IBAction)makePayment:(id)sender;

-(void)updatePeersCount;
-(void)updateFramesCount;
-(IBAction)sendOrder:(id)sender;
-(void)getData:(NSData *)data;
-(void)getDisconnectStatus;


@end
