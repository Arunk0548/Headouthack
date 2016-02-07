//
//  MainViewController.m
//  UDApp
//
//  Created by Tushar Verma on 06/02/16.
//  Copyright © 2016 Underdark. All rights reserved.
//

#import "MainViewController.h"
#import "UDApp-Swift.h"
#import "MConstants.h"
#import "MessageObject.h"

@implementation MainViewController

-(void)viewDidLoad{
    
    [super viewDidLoad];
    
    self.view.backgroundColor = [UIColor colorWithPatternImage:[UIImage imageNamed:@"master_BG"]];
    
    self.nodeObj = [Node new];
    
    self.nodeObj.controller = self;
    
    [self.nodeObj start];
    
    [self.menuTableView setDataSource:self];
    [self.menuTableView setDelegate:self];
    
    self.orderButton.enabled = NO;
    
    [self.coffeeTableView setDataSource:self];
    
    self.orderDict = [[NSMutableDictionary alloc]init];
    
    self.quantityLabel.text = @"0         £ 0.00";
    
    _msgObj = [[MessageObject alloc]init];
    
    self.currentViewTagValue = 0;
    
    _anounceValue = NO;
}

- (UIStatusBarStyle)preferredStatusBarStyle
{
    return UIStatusBarStyleLightContent;
}

-(void)updatePeersCount{
    
    self.orderButton.enabled = YES;
}

-(void)updateFramesCount{
    
    self.framesCountLabel.text = [NSString stringWithFormat:@"%ld frames",(long)self.nodeObj.framesCount];
}

-(void)getDisconnectStatus{
    
    self.orderButton.enabled = NO;
}

-(void)getData:(NSData *)data{
    
    NSMutableDictionary *res  = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableLeaves error:nil];
    
    if([[res objectForKey:@"message"]isEqual:@"3"]){
        
        if([[res objectForKey:@"messageType"]isEqualToString:@"2"]){
            
            self.hotChocolateImageView.image = [UIImage imageNamed:@"item_03_hot_chocolate_delayed"];
        }
        else {
            self.hotChocolateImageView.image = [UIImage imageNamed:@"item_03_hot_chocolate_served"];
        }
    }
    
    else if([[res objectForKey:@"message"]isEqualToString:@"1"]){
        
        self.greenTeaImageView.image = [UIImage imageNamed:@"item_01_green_tea_served"];
    }
    else if([[res objectForKey:@"message"]isEqualToString:@"2"]){
        
        self.espressoImageView.image = [UIImage imageNamed:@"item_02_espresso_served"];
    }
    else if([[res objectForKey:@"message"]isEqualToString:@"4"]){
        
        self.cappuccinoImageView.image = [UIImage imageNamed:@"item_04_cappuccino_served"];
    }
    else if([[res objectForKey:@"messageType"]isEqualToString:@"3"]){
        [self.orderStatusView setHidden:YES];
        [self.backBtn setHidden:YES];
        self.headerLabel.text = @"Invoice & Payment";
        [self.invoicePaymentView setHidden:NO];
    }
    
    else if ([[res objectForKey:@"messageType"]isEqualToString:@"7"]){
        if(_anounceValue == NO){
            _anounceValue = YES;
        [self.dealImageView setHidden:NO];
        self.dealImageView.frame = CGRectMake(200, 0, 0, 0);
        self.menuTableView.frame = CGRectMake(10, 0, 414, 650);
        [UIView beginAnimations:nil context:NULL];
        [UIView setAnimationDuration:1];
        [UIView  setAnimationDelegate:self];
        self.dealImageView.frame = CGRectMake(0, 0, 414, 200);
        self.menuTableView.frame = CGRectMake(10, 200, 414, 650);
        [UIView commitAnimations];
        }
    }
    
    else if([[res objectForKey:@"messageType"]isEqualToString:@"5"]){
        self.currentViewTagValue = 5;
        [self.backBtn setHidden:NO];
        [self.backBtn setImage:[UIImage imageNamed:@"home_icon"] forState:(UIControlStateNormal)];
        [self.backBtn setFrame:CGRectMake(20, 30, 20, 20)];
        self.paymentInfoImageView.image = [UIImage imageNamed:@"success_message"];
        
        self.hotChocolateImageView.image = [UIImage imageNamed:@"item_03_hot_chocolate_in_progress"];
        self.greenTeaImageView.image = [UIImage imageNamed:@"item_01_green_tea_in_progress"];
        self.espressoImageView.image = [UIImage imageNamed:@"item_02_espresso_in_progress"];
        self.cappuccinoImageView.image = [UIImage imageNamed:@"item_04_cappuccino_in_progress"];
    }
}


-(void)sendOrder:(id)sender{
    
    CATransition *transition = [CATransition animation];
    transition.duration = 0.5;
    transition.type = kCATransitionPush;
    transition.subtype = kCATransitionFromRight;
    [transition setTimingFunction:[CAMediaTimingFunction functionWithName:kCAMediaTimingFunctionEaseInEaseOut]];
    [self.view.layer addAnimation:transition forKey:nil];
    self.currentViewTagValue = 2;
    [self.coffeeView setHidden:YES];
    [self.coffeeTableView setHidden:YES];
    [self.headerLabel setText:@"Order Summary"];
    [self.orderSummaryView setHidden:NO];
    [self.summaryView setHidden:NO];
    
}


-(NSInteger)numberOfSectionsInTableView:(UITableView *)tableView{
    return 1;
}

-(NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    if(tableView == self.menuTableView){
        return 7;
    }
    else{
        return 5;
    }
}


-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath{
    
    if(tableView == self.menuTableView){
        
        CATransition *transition = [CATransition animation];
        transition.duration = 0.5;
        transition.type = kCATransitionPush;
        transition.subtype = kCATransitionFromRight;
        [transition setTimingFunction:[CAMediaTimingFunction functionWithName:kCAMediaTimingFunctionEaseInEaseOut]];
        [self.view.layer addAnimation:transition forKey:nil];
        
        [self.backBtn setHidden:NO];
        [self.backBtn setImage:[UIImage imageNamed:@"back_icon"] forState:(UIControlStateNormal)];
        [self.backBtn setFrame:CGRectMake(20, 25, 11, 24)];
        self.currentViewTagValue = 1;
        [self.sView setHidden:YES];
        [self.menuTableView setHidden:YES];
        [self.headerLabel setText:@"Coffee & Tea"];
        [self.coffeeView setHidden:NO];
        [self.coffeeTableView setHidden:NO];
        
    }
}

- (UITableViewCell*)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    if (tableView == self.menuTableView)
    {
        static NSString *simpleTableIdentifier = @"MainMenuTableCell";
        
        UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:simpleTableIdentifier];
        UIImageView *imgView;
        
        if (cell == nil) {
            cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:simpleTableIdentifier];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
            
        }
        cell.tag = indexPath.row;
        
        for(UIView *view in cell.contentView.subviews){
            if ([view isKindOfClass:[UIView class]]) {
                [view removeFromSuperview];
            }
        }
        imgView = [[UIImageView alloc] initWithFrame:CGRectMake(cell.contentView.frame.origin.x, 10, 400, 100)];
        
                switch (indexPath.row) {
            case 0:
                imgView.image = [UIImage imageNamed:@"menu_category_01"];
                break;
            case 1:
                imgView.image = [UIImage imageNamed:@"menu_category_02_active"];
                break;
            case 2:
                imgView.image = [UIImage imageNamed:@"menu_category_03"];
                break;
            case 3:
                imgView.image = [UIImage imageNamed:@"menu_category_04"];
                break;
            case 4:
                imgView.image = [UIImage imageNamed:@"menu_category_05"];
                break;
            case 5:
                imgView.image = [UIImage imageNamed:@"menu_category_06"];
                break;
            case 6:
                imgView.image = [UIImage imageNamed:@"menu_category_07"];
                break;
            case 7:
                imgView.image = [UIImage imageNamed:@"menu_category_08"];
                break;
                
            default:
                break;
        }
        cell.backgroundColor = [UIColor clearColor];
        [cell.contentView addSubview:imgView];
        
        return cell;
    }
    
    else {
        
        static NSString *simpleTableIdentifier = @"CoffeeAndTeaScreenCell";
        
        UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:simpleTableIdentifier];
        UIImageView *imgView;
        
        if (cell == nil) {
            cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:simpleTableIdentifier];
            cell.selectionStyle = UITableViewCellSelectionStyleNone;
        }
        cell.tag = indexPath.row;
        cell.backgroundColor = [UIColor clearColor];
        imgView = [[UIImageView alloc] initWithFrame:CGRectMake(cell.contentView.frame.origin.x, 10, 400, 110)];
        if (indexPath.row == 1)
        {
            [self createCellItemsForCell:cell forIndexPath:indexPath withImageView:imgView];
        }
        else
        {
            switch (indexPath.row) {
                case 0:
                    imgView.image = [UIImage imageNamed:@"food_item_01"];
                    break;
                case 1:
                    imgView.image = [UIImage imageNamed:@"food_item_02_active"];
                    break;
                case 2:
                    imgView.image = [UIImage imageNamed:@"food_item_03"];
                    break;
                case 3:
                    imgView.image = [UIImage imageNamed:@"food_item_04"];
                    break;
                case 4:
                    imgView.image = [UIImage imageNamed:@"food_item_05"];
                    break;
                default:
                    break;
            }
            
            cell.backgroundColor = [UIColor clearColor];
            [cell.contentView addSubview:imgView];
        }
        
        return cell;
    }
    
}

- (void)createCellItemsForCell:(UITableViewCell *)cell forIndexPath:(NSIndexPath *)indexPath withImageView:(UIImageView *)imgView
{
        if (self.coffeeView){
        {
            [cell.contentView.subviews makeObjectsPerformSelector: @selector(removeFromSuperview)];
            
            if(self.numberOfHotChocolateSelected>0)
            {
                imgView.image = [UIImage imageNamed:@"Hot_Chocolate_Base_Image"];
                //Counter Image 1
                UIImageView *imgCounter1 = [[UIImageView alloc] initWithFrame:CGRectMake(352, 16, 29, 29)];
                imgCounter1.image = [UIImage imageNamed:@"counter_detail_01"];
                //Plus button
                UIImageView *imgPluButton = [[UIImageView alloc] initWithFrame:CGRectMake(342, 65, 44, 40)];
                imgPluButton.image = [UIImage imageNamed:@"Add_Button_Hot_Choc.png"];
                UIButton *btnPLus = [UIButton buttonWithType:UIButtonTypeCustom];
                btnPLus.tag = indexPath.row;
                btnPLus.frame = CGRectMake(337, 60, 52, 50);
                //Minus button
                UIImageView *imgMinusButton = [[UIImageView alloc] initWithFrame:CGRectMake(280, 65, 44, 40)];
                imgMinusButton.image = [UIImage imageNamed:@"remove_button.png"];
                UIButton *btnMinus = [UIButton buttonWithType:UIButtonTypeCustom];
                btnMinus.tag = indexPath.row;
                [btnMinus addTarget:self action:@selector(btnRemoveHotChocolate:) forControlEvents:UIControlEventTouchUpInside];
                btnMinus.frame = CGRectMake(278, 65, 46, 44);
                
                [cell.contentView addSubview:imgView];
                [cell.contentView addSubview:imgCounter1];
                
                [cell.contentView addSubview:imgPluButton];
                [cell.contentView addSubview:btnPLus];
                
                [cell.contentView addSubview:imgMinusButton];
                [cell.contentView addSubview:btnMinus];
            }
            else
            {
                imgView.image = [UIImage imageNamed:@"food_item_02_active"];
                UIImageView *imgPluButton = [[UIImageView alloc] initWithFrame:CGRectMake(342, 65, 44, 40)];
                imgPluButton.image = [UIImage imageNamed:@"Add_Button_Hot_Choc.png"];
                UIButton *btnPLus = [UIButton buttonWithType:UIButtonTypeCustom];
                btnPLus.tag = indexPath.row;
                [btnPLus addTarget:self action:@selector(btnAddHotChocolate:) forControlEvents:UIControlEventTouchUpInside];
                btnPLus.frame = CGRectMake(337, 60, 52, 50);
                [cell.contentView addSubview:imgView];
                [cell.contentView addSubview:btnPLus];
                [cell.contentView addSubview:imgPluButton];
            }
        }
        
    }
    
}

-(void)btnAddHotChocolate:(UIControl *)btnHotChocolate
{
    self.numberOfHotChocolateSelected = self.numberOfHotChocolateSelected + 1;
    self.quantityLabel.text = [NSString stringWithFormat:@"%d         £ 1.25",self.numberOfHotChocolateSelected];
    
        NSArray *indexPathArray = [NSArray arrayWithObject:[NSIndexPath indexPathForRow:btnHotChocolate.tag inSection:0]];
        [self.coffeeTableView reloadRowsAtIndexPaths:indexPathArray withRowAnimation:UITableViewRowAnimationAutomatic];
    
}
- (void)btnRemoveHotChocolate:(UIControl *)btnHotChocolate
{
    self.numberOfHotChocolateSelected = self.numberOfHotChocolateSelected - 1;
    
    self.quantityLabel.text = @"0         £ 0.00";
    
    NSArray *indexPathArray = [NSArray arrayWithObject:[NSIndexPath indexPathForRow:btnHotChocolate.tag inSection:0]];
        [self.coffeeTableView reloadRowsAtIndexPaths:indexPathArray withRowAnimation:UITableViewRowAnimationAutomatic];
}

- (IBAction)orderConfirm:(id)sender {
    
    _msgObj.sourceId = [NSString  stringWithFormat:@"%lld",self.nodeObj.nodeId];
    _msgObj.destinationId = @"Master";
    _msgObj.messageType = [NSString stringWithFormat:@"%ld",(long)MTMessageType_ORDER];
    _msgObj.message = @"OrderPlaced";
    
    NSMutableDictionary* notifications = [[NSMutableDictionary alloc]init];
    [notifications setObject:_msgObj.dictionary forKey:@"items"];
    
    NSError *writeError = nil;
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:notifications options:NSJSONWritingPrettyPrinted error:&writeError];
    [self.nodeObj broadcastFrame:jsonData];
    
    CATransition *transition = [CATransition animation];
    transition.duration = 0.5;
    transition.type = kCATransitionPush;
    transition.subtype = kCATransitionFromRight;
    [transition setTimingFunction:[CAMediaTimingFunction functionWithName:kCAMediaTimingFunctionEaseInEaseOut]];
    [self.view.layer addAnimation:transition forKey:nil];
    self.currentViewTagValue = 3;
    [self.orderSummaryView setHidden:YES];
    [self.seatImageView setHighlighted:YES];
    [self.summaryView setHidden:YES];
    [self.headerLabel setText:@"Order Status"];
    [self.orderStatusView setHidden:NO];
    
}

- (IBAction)actionBack:(id)sender {
    
    if(self.currentViewTagValue == 3){
        
        CATransition *transition = [CATransition animation];
        transition.duration = 0.5;
        transition.type = kCATransitionPush;
        transition.subtype = kCATransitionFromLeft;
        [transition setTimingFunction:[CAMediaTimingFunction functionWithName:kCAMediaTimingFunctionEaseInEaseOut]];
        [self.view.layer addAnimation:transition forKey:nil];
        self.currentViewTagValue = 2;
        [self.orderSummaryView setHidden:NO];
        [self.seatImageView setHighlighted:NO];
        [self.summaryView setHidden:NO];
        [self.headerLabel setText:@"Order Summary"];
        [self.orderStatusView setHidden:YES];
    }
    
    else if(self.currentViewTagValue == 2){
        
        CATransition *transition = [CATransition animation];
        transition.duration = 0.5;
        transition.type = kCATransitionPush;
        transition.subtype = kCATransitionFromLeft;
        [transition setTimingFunction:[CAMediaTimingFunction functionWithName:kCAMediaTimingFunctionEaseInEaseOut]];
        [self.view.layer addAnimation:transition forKey:nil];
        self.currentViewTagValue = 1;
        [self.coffeeView setHidden:NO];
        [self.coffeeTableView setHidden:NO];
        [self.headerLabel setText:@"Coffee & Tea"];
        [self.orderSummaryView setHidden:YES];
    }
    
    else if(self.currentViewTagValue == 5){
        
        CATransition *transition = [CATransition animation];
        transition.duration = 0.5;
        transition.type = kCATransitionPush;
        transition.subtype = kCATransitionFromLeft;
        [transition setTimingFunction:[CAMediaTimingFunction functionWithName:kCAMediaTimingFunctionEaseInEaseOut]];
        [self.view.layer addAnimation:transition forKey:nil];
        self.currentViewTagValue = 0;
        [self.backBtn setHidden:YES];
        [self.paymentInitiated setHidden:YES];
        [self.sView setHidden:NO];
        [self.menuTableView setHidden:NO];
        [self.headerLabel setText:@"Food Menu"];
    }
    
    else {
        CATransition *transition = [CATransition animation];
        transition.duration = 0.5;
        transition.type = kCATransitionPush;
        transition.subtype = kCATransitionFromLeft;
        [transition setTimingFunction:[CAMediaTimingFunction functionWithName:kCAMediaTimingFunctionEaseInEaseOut]];
        [self.view.layer addAnimation:transition forKey:nil];
        [self.backBtn setHidden:YES];
        self.currentViewTagValue = 0;
        [self.sView setHidden:NO];
        [self.menuTableView setHidden:NO];
        [self.headerLabel setText:@"Food Menu"];
        [self.coffeeView setHidden:YES];
    }
}

- (IBAction)makePayment:(id)sender {
    
    _msgObj.sourceId = [NSString  stringWithFormat:@"%lld",self.nodeObj.nodeId];
    _msgObj.destinationId = @"Master";
    _msgObj.messageType = [NSString stringWithFormat:@"%ld",(long)MTMessageTYPE_MAKE_PAYMENT];
    _msgObj.message = @"PayemtInitaited";
    
    NSMutableDictionary* notifications = [[NSMutableDictionary alloc]init];
    
    [notifications setObject:_msgObj.dictionary forKey:@"items"];
    
    NSError *writeError = nil;
    NSData *jsonData = [NSJSONSerialization dataWithJSONObject:notifications options:NSJSONWritingPrettyPrinted error:&writeError];

    [self.nodeObj broadcastFrame:jsonData];
    
    [self.invoicePaymentView setHidden:YES];
    [self.paymentInitiated setHidden:NO];
    
}

-(void)didReceiveMemoryWarning{
    
}


@end
