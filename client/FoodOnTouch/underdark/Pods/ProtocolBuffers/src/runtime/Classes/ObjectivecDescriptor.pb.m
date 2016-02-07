// Generated by the protocol buffer compiler.  DO NOT EDIT!

#import "ObjectivecDescriptor.pb.h"
// @@protoc_insertion_point(imports)

@implementation ObjectivecDescriptorRoot
static id<PBExtensionField> ObjectivecDescriptorRoot_objectivecFileOptions = nil;
static PBExtensionRegistry* extensionRegistry = nil;
+ (PBExtensionRegistry*) extensionRegistry {
  return extensionRegistry;
}

+ (void) initialize {
  if (self == [ObjectivecDescriptorRoot class]) {
    ObjectivecDescriptorRoot_objectivecFileOptions =
      [PBConcreteExtensionField extensionWithType:PBExtensionTypeMessage
                                     extendedClass:[PBFileOptions class]
                                       fieldNumber:1002
                                      defaultValue:[ObjectiveCFileOptions defaultInstance]
                               messageOrGroupClass:[ObjectiveCFileOptions class]
                                        isRepeated:NO
                                          isPacked:NO
                            isMessageSetWireFormat:NO];
    PBMutableExtensionRegistry* registry = [PBMutableExtensionRegistry registry];
    [self registerAllExtensions:registry];
    [PBDescriptorRoot registerAllExtensions:registry];
    extensionRegistry = registry;
  }
}
+ (void) registerAllExtensions:(PBMutableExtensionRegistry*) registry {
  [registry addExtension:ObjectivecDescriptorRoot_objectivecFileOptions];
}
+ (id<PBExtensionField>) objectivecFileOptions {
  return ObjectivecDescriptorRoot_objectivecFileOptions;
}
@end

@interface ObjectiveCFileOptions ()
@property (strong) NSString* package;
@property (strong) NSString* classPrefix;
@end

@implementation ObjectiveCFileOptions

- (BOOL) hasPackage {
  return !!hasPackage_;
}
- (void) setHasPackage:(BOOL) _value_ {
  hasPackage_ = !!_value_;
}
@synthesize package;
- (BOOL) hasClassPrefix {
  return !!hasClassPrefix_;
}
- (void) setHasClassPrefix:(BOOL) _value_ {
  hasClassPrefix_ = !!_value_;
}
@synthesize classPrefix;
- (instancetype) init {
  if ((self = [super init])) {
    self.package = @"";
    self.classPrefix = @"";
  }
  return self;
}
static ObjectiveCFileOptions* defaultObjectiveCFileOptionsInstance = nil;
+ (void) initialize {
  if (self == [ObjectiveCFileOptions class]) {
    defaultObjectiveCFileOptionsInstance = [[ObjectiveCFileOptions alloc] init];
  }
}
+ (instancetype) defaultInstance {
  return defaultObjectiveCFileOptionsInstance;
}
- (instancetype) defaultInstance {
  return defaultObjectiveCFileOptionsInstance;
}
- (BOOL) isInitialized {
  return YES;
}
- (void) writeToCodedOutputStream:(PBCodedOutputStream*) output {
  if (self.hasPackage) {
    [output writeString:1 value:self.package];
  }
  if (self.hasClassPrefix) {
    [output writeString:2 value:self.classPrefix];
  }
  [self.unknownFields writeToCodedOutputStream:output];
}
- (SInt32) serializedSize {
  __block SInt32 size_ = memoizedSerializedSize;
  if (size_ != -1) {
    return size_;
  }

  size_ = 0;
  if (self.hasPackage) {
    size_ += computeStringSize(1, self.package);
  }
  if (self.hasClassPrefix) {
    size_ += computeStringSize(2, self.classPrefix);
  }
  size_ += self.unknownFields.serializedSize;
  memoizedSerializedSize = size_;
  return size_;
}
+ (ObjectiveCFileOptions*) parseFromData:(NSData*) data {
  return (ObjectiveCFileOptions*)[[[ObjectiveCFileOptions builder] mergeFromData:data] build];
}
+ (ObjectiveCFileOptions*) parseFromData:(NSData*) data extensionRegistry:(PBExtensionRegistry*) extensionRegistry {
  return (ObjectiveCFileOptions*)[[[ObjectiveCFileOptions builder] mergeFromData:data extensionRegistry:extensionRegistry] build];
}
+ (ObjectiveCFileOptions*) parseFromInputStream:(NSInputStream*) input {
  return (ObjectiveCFileOptions*)[[[ObjectiveCFileOptions builder] mergeFromInputStream:input] build];
}
+ (ObjectiveCFileOptions*) parseFromInputStream:(NSInputStream*) input extensionRegistry:(PBExtensionRegistry*) extensionRegistry {
  return (ObjectiveCFileOptions*)[[[ObjectiveCFileOptions builder] mergeFromInputStream:input extensionRegistry:extensionRegistry] build];
}
+ (ObjectiveCFileOptions*) parseFromCodedInputStream:(PBCodedInputStream*) input {
  return (ObjectiveCFileOptions*)[[[ObjectiveCFileOptions builder] mergeFromCodedInputStream:input] build];
}
+ (ObjectiveCFileOptions*) parseFromCodedInputStream:(PBCodedInputStream*) input extensionRegistry:(PBExtensionRegistry*) extensionRegistry {
  return (ObjectiveCFileOptions*)[[[ObjectiveCFileOptions builder] mergeFromCodedInputStream:input extensionRegistry:extensionRegistry] build];
}
+ (ObjectiveCFileOptionsBuilder*) builder {
  return [[ObjectiveCFileOptionsBuilder alloc] init];
}
+ (ObjectiveCFileOptionsBuilder*) builderWithPrototype:(ObjectiveCFileOptions*) prototype {
  return [[ObjectiveCFileOptions builder] mergeFrom:prototype];
}
- (ObjectiveCFileOptionsBuilder*) builder {
  return [ObjectiveCFileOptions builder];
}
- (ObjectiveCFileOptionsBuilder*) toBuilder {
  return [ObjectiveCFileOptions builderWithPrototype:self];
}
- (void) writeDescriptionTo:(NSMutableString*) output withIndent:(NSString*) indent {
  if (self.hasPackage) {
    [output appendFormat:@"%@%@: %@\n", indent, @"package", self.package];
  }
  if (self.hasClassPrefix) {
    [output appendFormat:@"%@%@: %@\n", indent, @"classPrefix", self.classPrefix];
  }
  [self.unknownFields writeDescriptionTo:output withIndent:indent];
}
- (void) storeInDictionary:(NSMutableDictionary *)dictionary {
  if (self.hasPackage) {
    [dictionary setObject: self.package forKey: @"package"];
  }
  if (self.hasClassPrefix) {
    [dictionary setObject: self.classPrefix forKey: @"classPrefix"];
  }
  [self.unknownFields storeInDictionary:dictionary];
}
- (BOOL) isEqual:(id)other {
  if (other == self) {
    return YES;
  }
  if (![other isKindOfClass:[ObjectiveCFileOptions class]]) {
    return NO;
  }
  ObjectiveCFileOptions *otherMessage = other;
  return
      self.hasPackage == otherMessage.hasPackage &&
      (!self.hasPackage || [self.package isEqual:otherMessage.package]) &&
      self.hasClassPrefix == otherMessage.hasClassPrefix &&
      (!self.hasClassPrefix || [self.classPrefix isEqual:otherMessage.classPrefix]) &&
      (self.unknownFields == otherMessage.unknownFields || (self.unknownFields != nil && [self.unknownFields isEqual:otherMessage.unknownFields]));
}
- (NSUInteger) hash {
  __block NSUInteger hashCode = 7;
  if (self.hasPackage) {
    hashCode = hashCode * 31 + [self.package hash];
  }
  if (self.hasClassPrefix) {
    hashCode = hashCode * 31 + [self.classPrefix hash];
  }
  hashCode = hashCode * 31 + [self.unknownFields hash];
  return hashCode;
}
@end

@interface ObjectiveCFileOptionsBuilder()
@property (strong) ObjectiveCFileOptions* resultObjectiveCfileOptions;
@end

@implementation ObjectiveCFileOptionsBuilder
@synthesize resultObjectiveCfileOptions;
- (instancetype) init {
  if ((self = [super init])) {
    self.resultObjectiveCfileOptions = [[ObjectiveCFileOptions alloc] init];
  }
  return self;
}
- (PBGeneratedMessage*) internalGetResult {
  return resultObjectiveCfileOptions;
}
- (ObjectiveCFileOptionsBuilder*) clear {
  self.resultObjectiveCfileOptions = [[ObjectiveCFileOptions alloc] init];
  return self;
}
- (ObjectiveCFileOptionsBuilder*) clone {
  return [ObjectiveCFileOptions builderWithPrototype:resultObjectiveCfileOptions];
}
- (ObjectiveCFileOptions*) defaultInstance {
  return [ObjectiveCFileOptions defaultInstance];
}
- (ObjectiveCFileOptions*) build {
  [self checkInitialized];
  return [self buildPartial];
}
- (ObjectiveCFileOptions*) buildPartial {
  ObjectiveCFileOptions* returnMe = resultObjectiveCfileOptions;
  self.resultObjectiveCfileOptions = nil;
  return returnMe;
}
- (ObjectiveCFileOptionsBuilder*) mergeFrom:(ObjectiveCFileOptions*) other {
  if (other == [ObjectiveCFileOptions defaultInstance]) {
    return self;
  }
  if (other.hasPackage) {
    [self setPackage:other.package];
  }
  if (other.hasClassPrefix) {
    [self setClassPrefix:other.classPrefix];
  }
  [self mergeUnknownFields:other.unknownFields];
  return self;
}
- (ObjectiveCFileOptionsBuilder*) mergeFromCodedInputStream:(PBCodedInputStream*) input {
  return [self mergeFromCodedInputStream:input extensionRegistry:[PBExtensionRegistry emptyRegistry]];
}
- (ObjectiveCFileOptionsBuilder*) mergeFromCodedInputStream:(PBCodedInputStream*) input extensionRegistry:(PBExtensionRegistry*) extensionRegistry {
  PBUnknownFieldSetBuilder* unknownFields = [PBUnknownFieldSet builderWithUnknownFields:self.unknownFields];
  while (YES) {
    SInt32 tag = [input readTag];
    switch (tag) {
      case 0:
        [self setUnknownFields:[unknownFields build]];
        return self;
      default: {
        if (![self parseUnknownField:input unknownFields:unknownFields extensionRegistry:extensionRegistry tag:tag]) {
          [self setUnknownFields:[unknownFields build]];
          return self;
        }
        break;
      }
      case 10: {
        [self setPackage:[input readString]];
        break;
      }
      case 18: {
        [self setClassPrefix:[input readString]];
        break;
      }
    }
  }
}
- (BOOL) hasPackage {
  return resultObjectiveCfileOptions.hasPackage;
}
- (NSString*) package {
  return resultObjectiveCfileOptions.package;
}
- (ObjectiveCFileOptionsBuilder*) setPackage:(NSString*) value {
  resultObjectiveCfileOptions.hasPackage = YES;
  resultObjectiveCfileOptions.package = value;
  return self;
}
- (ObjectiveCFileOptionsBuilder*) clearPackage {
  resultObjectiveCfileOptions.hasPackage = NO;
  resultObjectiveCfileOptions.package = @"";
  return self;
}
- (BOOL) hasClassPrefix {
  return resultObjectiveCfileOptions.hasClassPrefix;
}
- (NSString*) classPrefix {
  return resultObjectiveCfileOptions.classPrefix;
}
- (ObjectiveCFileOptionsBuilder*) setClassPrefix:(NSString*) value {
  resultObjectiveCfileOptions.hasClassPrefix = YES;
  resultObjectiveCfileOptions.classPrefix = value;
  return self;
}
- (ObjectiveCFileOptionsBuilder*) clearClassPrefix {
  resultObjectiveCfileOptions.hasClassPrefix = NO;
  resultObjectiveCfileOptions.classPrefix = @"";
  return self;
}
@end


// @@protoc_insertion_point(global_scope)
