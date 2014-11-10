

//------------------------------------
//*********Util.js   ***********
//------------------------------------

alert("in WidgetHelp");

var obj2Json = function(str){
	str = obj2Str(str);
	str = str.replace(/\\/g, "\\\\");  
	return eval('('+str+')');
};
   
var obj2Str = function(str){
	return str+'';
};
 
var jsobj2File = function(fileObj){
	if(fileObj==null)
		return null;
	var file=new File();
	var v;
	for(v in file)
	{
		file[v]=fileObj[v];
	}
	file.createDate=new Date(fileObj.createDate);
	file.lastModifyDate=new Date(fileObj.lastModifyDate);
	return file;
}
 
var jsobj2Str = function(jsobj){
	var str="";
	var v;
	for(v in jsobj)
	{
		if(jsobj[v]!=undefined){
			if(typeof jsobj[v]=="string")
			{
				str+=",";
				str+="\""+v+"\":";
				str+="\""+jsobj[v]+"\"";
			}
			else if(jsobj[v].constructor == "Date" ){
				str+=",";
				str+="\""+v+"\":";
				str+=jsobj[v].getTime();
			}
			//else str+=jsobj[v];
		}
	}
	str=str.substr(1);
	str="{"+str+"}";
	return str;
}

var jsobj2Str_acc = function(jsobj){
	var str="";
	var v;
	for(v in jsobj)
	{
		if(jsobj[v]!=undefined){
			if(typeof jsobj[v]=="string")
			{
				str+=",";
				str+="'"+v+"':";
				str+="'"+jsobj[v]+"'";
			}
			else if(jsobj[v].constructor == "Date" ){
				str+=",";
				str+="'"+v+"':";
				str+=jsobj[v].getTime();
			}
			//else str+=jsobj[v];
		}
	}
	str=str.substr(1);
	str="{"+str+"}";
	return str;
}

//------------------------------------
//*********AccelerometerInfo.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to be used for listing all functions and properties of 
 * JavaScript objects: AccelerometerInfo.
 *
 * @version 0.1 
 */
AccelerometerInfo.prototype = new Object();

/**
 * @class AccelerometerInfo is a sub-object of <I>DeviceStateInfo</I>.<br>
 */
function AccelerometerInfo() {
}

/**
 * The value of x-axis. This field should be a double value.
 *
 * @type Number
 */
AccelerometerInfo.prototype.getxAxis = function(){
    return AccelerometerInfoJava.getxAxis();
};

/**
 * The value of y-axis. This field should be a double value.
 *
 * @type Number
 */
AccelerometerInfo.prototype.getyAxis = function(){
    return AccelerometerInfoJava.getyAxis();
};
 
 
/**
 * The value of z-axis. This field should be a double value.
 *
 * @type Number
 */
AccelerometerInfo.prototype.getzAxis = function(){
    return AccelerometerInfoJava.getzAxis();
};


AccelerometerInfo.prototype.watchAcceleration = function(x,y,z) {
};

//------------------------------------
//*********Account.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to be used for listing all functions and properties of 
 * JavaScript objects: Account.
 *
 * @version 0.1 
 */
Account.prototype = new Object();

/**
 * @class Account defines the Email account Object which is referenced by some of 
 * Messaging Service APIs. <br>
 * 
 * @constructor
 */
function Account() {
};

/**
 * Identifier for Email Account ID. <br>
 * 
 * @type String
 */
Account.prototype.accountId = new String();

/**
 * Identifier for Email account Name. <br>
 *
 * @type String
 */
Account.prototype.accountName = new String();



//------------------------------------
//*********AccountInfo.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to list all functions and properties of 
 * JavaScript object: AccountInfo.
 *
 * @version 0.1 
 */
AccountInfo.prototype = new Object();

/**
 * @class AccountInfo is a sub-object of <I>Widget.Device</I>.<br>
 * <br>
 * This object provides read-only information about the user account associated 
 * with the phone. This relies on back-end network services in order to obtain 
 * this information.<br>
 * <br>
 * This information will be undefined if the back-end services are unavailable 
 * (e.g., due to network unavailability) or if the Widget does not have sufficient
 * security permissions to obtain this information. <br>
 *
 */
function AccountInfo() {
};

/**
 * This is a value guaranteed to be unique to the user account. 
 * 
 * @type String
 */
AccountInfo.prototype.getphoneUserUniqueId = function() {
    return obj2Str(AccountInfoJava.getphoneUserUniqueId());
};

/**
 * This is the mobile phone number associated with the account. This corresponds to 
 * the dialed mobile number and not necessarily to the phone's internal Mobile 
 * Identification Number (MIN). 
 * 
 * @type String
 */
AccountInfo.prototype.getphoneMSISDN = function() {
    return obj2Str(AccountInfoJava.getphoneMSISDN());
};

/**
 * This is the name of the operator hosting the user account.
 * 
 * @type String
 */
AccountInfo.prototype.getphoneOperatorName = function() {
    return obj2Str(AccountInfoJava.getphoneOperatorName());
};

/**
 * This is the financial account balance in the default currency for the user account. 
 * This field should be double value.This is expected to use network resources.
 * 
 * @type Number
 */
AccountInfo.prototype.getuserAccountBalance = function() {
    return AccountInfoJava.getuserAccountBalance();
};

/**
 * This indicates the type of billing for the account. Valid values include 鈥榩repaid鈥� 
 * 'postpaid', and 'other'. This is expected to use network resources.
 * 
 * @type String
 */
AccountInfo.prototype.getuserSubscriptionType = function() {
    return obj2Str(AccountInfoJava.getuserSubscriptionType());
};




//------------------------------------
//*********AddressBookItem.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to list all functions and properties of 
 * JavaScript object: AddressBookItem.
 *
 * @version 0.1 
 */
AddressBookItem.prototype = new Object();

/**
 * @class AddressBookItem represents a PIM Contact. A contact consists of values 
 * associated with attributes. Its possible values are "addressBookItemId", "fullName",
 * "mobilePhone", "homePhone", "workPhone", "eMail", "company", "title", "address". <br>
 */
function AddressBookItem() {
};

//Widget.PIM.AddressBookItem.prototype= new AddressBookItem();

/** 
 * This is a unique indicator (potentially assigned by the WRT) for identifying the contact.
 * 
 * @type String
 */
AddressBookItem.prototype.addressBookItemId = "";

/**
 * This is the full name of the contact.
 * 
 * @type String
 */
AddressBookItem.prototype.fullName = "";

/**
 * This is the mobile phone number of the contact.
 * 
 * @type String
 */
AddressBookItem.prototype.mobilePhone = "";

/**
 * This is the home phone number of the contact.
 * 
 * @type String
 */
AddressBookItem.prototype.homePhone = "";

/**
 * This is the work phone number of the contact.
 * 
 * @type String
 */
AddressBookItem.prototype.workPhone = "";

/**
 * This is the email address of the contact.
 * 
 * @type String
 */
AddressBookItem.prototype.eMail = "";

/**
 * This is the company name of the contact.
 * 
 * @type String
 */
AddressBookItem.prototype.company = "";

/**
 * This is the title of the contact.
 * 
 * @type String
 */
AddressBookItem.prototype.title = "";

/**
 * This is the address of the contact.
 * 
 * @type String
 */
AddressBookItem.prototype.address = "";

/**
 * Assigns a value for the specified attribute. All attribute but "addressBookItemId" 
 * can be set a new value. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var contact = new Widget.PIM.AddressBookItem();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;contact.setAttributeValue("fullName", "George Brown");
 * 
 * @param {String} attribute this parameter should reference PIM contact attributes values
 * @param {String} value the value to be assigned
 * 
 * @returns
 * @type void
 */
AddressBookItem.prototype.setAttributeValue = function(attribute, value) {
	return this[attribute]=value;
};

/**
 * Puts the contact into multiple contact groups. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var contact = Widget.PIM.getAddressBookItem("1");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; var groups = new Array("group1", "group2");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;contact.setAddressGroupNames(groups);<br>
 * 
 * @param {Array} groups an array of String containing the group names that the contact 
 * is going to be put in 
 * 
 * @returns
 * @type void
 */
AddressBookItem.prototype.setAddressGroupNames = function(groups) {
};

/**
 * The value associated with the specified attribute. <br> 
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.PIM.onAddressBookItemsFound = myCallBack;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var myContact = new Widget.PIM.AddressBookItem();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;myContact.setAttributeValue("mobilePhone", "10086");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.PIM.findAddressBookItems(myContact, 0, 9);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallBack(addressBookItems) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;var contact = addressBookItems[0];<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;var name = contact.getAttributeValue("fullName");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br>
 * 
 * @param {String} attribute one of the attribute fields of the <i>AddressBookItem</i> object
 *
 * @returns the associated value
 * @type String
 */
AddressBookItem.prototype.getAttributeValue = function(attribute) {
	return this[attribute];
};

/**
 * Returns the group names the contact belongs to. <br> 
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var contact = Widget.PIM.getAddressBookItem("1");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var groups = contact.getAddressGroupNames();<br>
 * 
 * @returns an array of group names that the contact belongs to. Each element in the 
 * array should be a string.
 * @type Array
 */
AddressBookItem.prototype.getAddressGroupNames = function() {
	return new Array();
};

/**
 * Returns the attributes assigned to the contact. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var contact = new Widget.PIM.AddressBookItem();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var attributeValue = contact.getAvailableAttributes();
 * 
 * @returns an array of attributes having assigned values or an empty array 
 * if none are assigned. Each element in the array should be a string. 
 * @type Array
 */
AddressBookItem.prototype.getAvailableAttributes = function() {
	var array = new Array();
};

/**
 * Field changes to the addressbookItem will be applied after this method is invoked. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.PIM.onAddressBookItemsFound = myCallBack;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var myContact = new Widget.PIM.AddressBookItem();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;myContact.setAttributeValue("mobilePhone", "10086");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.PIM.findAddressBookItems(myContact, 0, 9);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallBack(addressBookItems) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;var contact = addressBookItems[0];<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contact.setAttributeValue("mobilePhone", "12580");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;contact.update();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br>
 * 
 * @returns 
 * @type void
 */
AddressBookItem.prototype.update = function() {
	AddressBookItemJava.update(jsobj2Str(this));
};
 
var ArrayToAddressBookItem = function(array){
	var item = new AddressBookItem();
	item.address = array.address;
	item.addressBookItemId = array.addressBookItemId;
	item.company = array.company;
	item.eMail = array.eMail;
	item.fullName = array.fullName;
	item.homePhone = array.homePhone;
	item.workPhone = array.workPhone;
	item.title = array.title;
	item.mobilePhone = array.mobilePhone;
	return item;
};

//------------------------------------
//*********Attachment.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to be used for listing all functions and properties of 
 * JavaScript objects: Attachment.
 *
 * @version 0.1 
 */
Attachment.prototype = new Object();

/**
 * @class Attachment is a class which represents an Attachment. It provides 
 * important information about an Attachment.<br>
 *
 * @constructor
 */
function Attachment() {
};
//Widget.Messaging.Attachment.prototype= new Attachment();
/**
 * The name of this Attachment. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var attachment = new Widget.Messaging.Attachment();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;attachment.fileName = "/local/test.mp3";
 * 
 * @type String
 */
Attachment.prototype.fileName = "";
Attachment.prototype.filePath = "";

/**
 * The MIME type of this Attachment.
 * 
 * @type String
 */
Attachment.prototype.MIMEType = "";

/**
 * The size of this Attachment. The units are bytes. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var attachment = new Widget.Messaging.Attachment();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;attachment.size = 1024;
 * 
 * @type Number
 */
Attachment.prototype.size = new Number();


//------------------------------------
//*********AudioPlayer.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to be used for listing all functions and properties of 
 * JavaScript objects: AudioPlayer.
 *
 * @version 0.1 
 */
AudioPlayer.prototype = new Object();

/**
 * @class AudioPlayer is the object for JIL Audio player capabilities.<br>
 * <br>
 * AudioPlayer is a sub-object of <I>Multimedia</I>. We use AudioPlayer in this way:
 * Widget.Multimedia.AudioPlayer.XXX<br>
 */
function AudioPlayer() {
};

/**
 * Open an audio file which will be played by <i>play</i> function. <br> 
 * The widget engine is responsible for file caching, etc. The following protocols 
 * are supported by the open() function: "file://", "rtsp://","http://".<br>
 * If the parameter string doesn't start with the protocol syntax listed above, 
 * the widget engine will search the media files in the widget package.<br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.AudioPlayer.open("file:///sdcard/sample.mp3");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.AideoPlayer.open("sample.mp3");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.AudioPlayer.open("http://www.sample.com/sample.mp3");
 * 
 * @param {String} fileUrl the url of the audio file to be played.
 * 
 * @returns
 * @type void
 */
AudioPlayer.prototype.open = function(fileUrl) {
	AudioPlayerJava.open(fileUrl);
	
};

/**
 * Play the audio which is opened by <i>open</i> function. <br> 
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.AudioPlayer.open("sample.mp3");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.AudioPlayer.play(5);<br>
 * 
 * @param {Number} repeatTimes an integer value specifies how many times the 
 * media file should be played.
 * 
 * @returns
 * @type void
 */
AudioPlayer.prototype.play = function(repeatTimes) {
	AudioPlayerJava.play(repeatTimes);
};

/**
 * Pause the audio which is played by <i>play</i> function. <br> 
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.AudioPlayer.onStateChange = myCallback(state);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.AudioPlayer.open("file:///sdcard/sample.mp3");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.AudioPlayer.play(5);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallback(state) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if (state == "playing") {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.AudioPlayer.pause();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br>
 * 
 * @returns
 * @type void
 */
AudioPlayer.prototype.pause = function() {
	AudioPlayerJava.pause();
};

/**
 * Resume the audio which is paused by <i>pause</i> function. <br> 
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.AudioPlayer.onStateChange = myCallback(state);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.AudioPlayer.open("file:///sdcard/sample.mp3");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.AudioPlayer.play(5);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.AudioPlayer.pause();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallback(state) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if (state == "paused") {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.AudioPlayer.resume();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br>
 * 
 * @returns
 * @type void
 */
AudioPlayer.prototype.resume = function() {
	AudioPlayerJava.resume();
};

/**
 * Stop the audio which is played by <i>play</i> function. <br> 
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.AudioPlayer.onStateChange = myCallback(state);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.AudioPlayer.open("file:///sdcard/sample.mp3");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.AudioPlayer.play(5);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallback(state) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if (state == "playing") {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.AudioPlayer.stop();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br>
 * 
 * @returns
 * @type void
 */
AudioPlayer.prototype.stop = function() {
	AudioPlayerJava.stop();
};

/**
 * Call back method to invoke when the state of the audio player changes. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.AudioPlayer.onStateChange = myCallback(state);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.AudioPlayer.open("file:///sdcard/sample.mp3");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallback(state) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if (state == "opened") {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.AudioPlayer.play();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *
 * @param {String} state indicating the current state of the audio player. 
 * Possible values are "InitStatus", "opened","stopped" "paused", and "playing".
 *
 * @returns
 * @type void
 */
AudioPlayer.prototype.onStateChange = function(state) {
}


//------------------------------------
//*********EventRecurrenceTypes.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to list all functions and properties of 
 * JavaScript object: EventRecurrenceTypes.
 *
 * @version 0.1 
 */
EventRecurrenceTypes.prototype = new Object();

/**
 * @class EventRecurrenceTypes represents the reminder you can select.<br>
 */
function EventRecurrenceTypes() {
};

/** 
 * This represents the reminder will not repeat.
 * 
 * @type String
 */
EventRecurrenceTypes.prototype.NOT_REPEAT = "NOT_REPEAT";

/**
 * This represents the reminder will  repeat daily.
 * 
 * @type String
 */
EventRecurrenceTypes.prototype.DAILY = "DAILY";

/**
 * This represents the reminder will repeat on weekday.
 * 
 * @type String
 */
EventRecurrenceTypes.prototype.EVERY_WEEKDAY = "EVERY_WEEKDAY";

/**
 * This represents the reminder will repeat every week.
 * 
 * @type String
 */
EventRecurrenceTypes.prototype.WEEKLY_ON_DAY = "WEEKLY_ON_DAY";

/**
 * This represents the reminder will repeat monthly, on the weekday every month.
 * 
 * @type String
 */
EventRecurrenceTypes.prototype.MONTHLY_ON_DAY = "MONTHLY_ON_DAY";

/**
 * This represents the reminder will repeat monthly, on the monthday every month.
 * 
 * @type String
 */
EventRecurrenceTypes.prototype.MONTHLY_ON_DAY_COUNT = "MONTHLY_ON_DAY_COUNT";

/**
 * This represents the reminder will repeat yearly.
 * 
 * @type String
 */
EventRecurrenceTypes.prototype.YEARLY = "YEARLY";


//------------------------------------
//*********CalendarItem.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to list all functions and properties of 
 * JavaScript object: CalendarItem.
 *
 * @version 0.1 
 */
CalendarItem.prototype = new Object();

/**
 * @class CalendarItem represents a PIM calendar event. A calendar event consists of 
 * values associated with attributes, such as eventName, alarmDate, etc.<br>
 */
function CalendarItem() {
};
//Widget.PIM.CalendarItem.prototype= new CalendarItem();
/** 
 * A unique indicator (potentially assigned by the WRT) for identifying the calendar event
 * 
 * @type String
 */
CalendarItem.prototype.calendarItemId = "";

/**
 * Alarm time of this event
 * 
 * @type Date
 */
CalendarItem.prototype.alarmDate;

/**
 * Start time of this event
 * 
 * @type Date
 */
CalendarItem.prototype.eventStartTime;

/**
 * End time of this event
 * 
 * @type Date
 */
CalendarItem.prototype.eventEndTime;  

/**
 * Subject of this event.
 * 
 * @type String
 */
CalendarItem.prototype.eventName = "";

/**
 * Description of this event
 * 
 * @type String
 */
CalendarItem.prototype.eventNotes = "";
	
/** 
 * Recurrent interval of this event.The values are defined in 
 * EventRecurrenceTypes Object
 * 
 * 
 * @type {EventRecurrenceTypes}
 */
CalendarItem.prototype.eventRecurrence = "";
// new EventRecurrenceTypes();

/**
 * Indicates whether an alarm is to be activated for the event
 * 
 * @type Boolean
 */
CalendarItem.prototype.alarmed = new Boolean();

/**
 * The field changes of the calendarItem will apply after this method is invoked. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.PIM.onCalendarItemAlert = myCallBack;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var myCalendarItem = new Widget.PIM.CalendarItem();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;myCalendarItem.eventStartTime = new Date(2008, 9, 22, 0, 0, 0);<br> 
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.PIM.findCalendarItems(myCalendarItem, 0, 9);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallBack(calendarItems) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;var calendarItem = calendarItems[0];<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;calendarItem.eventStartTime = new Date(2008, 9, 23, 0, 0, 0);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;calendarItem.update(); <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br>
 * 
 * @returns 
 * @type void     
 */
CalendarItem.prototype.update = function() {
	alert(this.toJSONString());
	CalendarItemJava.update(this.toJSONString());
};
 
CalendarItem.prototype.toJSONString = function() {
	var str= '{"calendarItemId":"';
	str += this.calendarItemId;
	str += '","alarmDate":"';
	if(this.alarmDate != undefined){	
		str += this.alarmDate.getTime();
	}
	str += '","eventStartTime":"';
	if(this.eventStartTime != undefined){	
	str += this.eventStartTime.getTime();
	}
	str += '","eventEndTime":"';
	if(this.eventEndTime != undefined){	
	str += this.eventEndTime.getTime();
	}
	str += '","eventName":"';
	str += this.eventName;
	str += '","eventNotes":"';
	str += this.eventNotes;
	str += '","eventRecurrence":"';
	str += this.eventRecurrence;
	str += '","alarmed":"';
	str += this.alarmed;
	str += '"}'; 
	return str;  
}

var ArrayToCalendarItem = function(array){
	var item = new CalendarItem();
	item.calendarItemId = array.calendarItemId;
	item.alarmDate = new Date(array.alarmDate);
	item.eventStartTime = new Date(array.eventStartTime);
	item.eventEndTime = new Date(array.eventEndTime);
	item.eventName = array.eventName;
	item.eventNotes = array.eventNotes;
	item.eventRecurrence = array.eventRecurrence;
	item.alarmed = array.alarmed;
	return item;
};

//------------------------------------
//*********CallRecord.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to list all functions and properties of 
 * JavaScript object: CallRecord.
 *
 * @version 0.1 
 */
CallRecord.prototype = new Object();

/**
 * @class CallRecord represents the record items in the call log.<br>
 */
function CallRecord() {
};
//Widget.Telephony.CallRecord.prototype= new CallRecord();
/**
 * A String indicating the type of call record, i.e. "received", "outgoing", "missed".
 * This corresponds to the constants defined in the CallRecordType object.
 * 
 * @type String
 */
CallRecord.prototype.callRecordType = "";

/**
 * A unique indicator (potentially assigned by the WRT) for identifying the call record.
 * 
 * @type String
 */
CallRecord.prototype.callRecordId = "";

/**
 * The phone number on call record. <br>
 * <br>
 * If this is an received or missed call record, then this is the phone number of 
 * the party originating the call. <br>
 * <br>
 * If this is an outgoing call record, then this is the phone number of the party 
 * receiving the call. 
 * 
 * @type String
 */
CallRecord.prototype.callRecordAddress = "";

/**
 * The addressbook name on call record. <br>
 * <br>
 * If this is an received or missed call record, then this is the name from the 
 * address book that the phone was able to associate with the source phone number, 
 * if any applies. <br>
 * <br>
 * If this is an outgoing call record, then this is the name from the address 
 * book that the phone was able to associate with the destination phone number, 
 * if any applies.
 * 
 * @type String
 */
CallRecord.prototype.callRecordName = "";

/**
 * The number of seconds occupied by the call. This field should be an integer 
 * value. Due to limitation of some handsets, this could be an approximate value.
 * 
 * @type Number
 */
CallRecord.prototype.durationSeconds = new Number();

/**
 * The time and date when the call begins. 
 * 
 * @type Date
 */
CallRecord.prototype.startTime = new Date();


//------------------------------------
//*********CallRecordTypes.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to list all functions and properties of 
 * JavaScript object: CallRecordTypes.
 *
 * @version 0.1 
 */
CallRecordTypes.prototype = new Object();

/**
 * @class CallRecordTypes represents the type of call record in the call log. <br>
 * <br>
 * CallRecordTypes is a sub-object of <I>Widget.Telephony</I>. <br>
 */
function CallRecordTypes() {
};

/**
 * The identifier used for an RECEIVED Call Record that is received and not missed,
 * referenced by Telephony and CallRecord.
 * 
 * @type String
 */
CallRecordTypes.prototype.RECEIVED = "received";

/**
 * The identifier used for an OUTGOING Call Record, 
 * referenced by Telephony and CallRecord. 
 * 
 * @type String
 */
CallRecordTypes.prototype.OUTGOING = "outgoing";
    
/**
 * The identifier used for an MISSED Call Record, 
 * referenced by Telephony and CallRecord. 
 * 
 * @type String
 */
CallRecordTypes.prototype.MISSED = "missed";


//------------------------------------
//*********Camera.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to be used for listing all functions and properties of 
 * JavaScript objects: Camera.
 *
 * @version 0.1 
 */
Camera.prototype = new Object();

/**
 * @class Camera is the object for JIL Camera capabilities<br>
 * <br>
 * Camera is a sub-object of <I>Multimedia</I>. We use AudioPlayer in this way:
 * Widget.Multimedia.Camera.XXX<br>
 */
function Camera() {
};

/**
 * Capture still image and save to a file. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.Camera.onCameraCaptured = myCallBack;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.Camera.captureImage("/sdcard/a.jpg", false);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallBack(String fileName) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;var v = fileName;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;} <br>
 * 
 * @param {String} fileName the relative path where the captured image to be 
 * saved. If filename includes a fullPath, then image file is saved to
 *  that exact location.
 * @param {Boolean} lowRes the flag to specify if low resolution is used. 
 *    	    
 * @returns the full path of the saved file. 
 * This value is identical to the file path provided by onCameraCaptured().
 * @type String
 */
Camera.prototype.captureImage = function(fileName,lowRes) {
	CameraCaptureJava.captureImage(fileName,lowRes);
};
/**
 * This method attaches the camera to a DOM object which specifies the preview <br>
 * window before taking a picture or recording a video.Note:The size of Preview<br>
 * Window should larger than 32(pixel) * 24(pixel)
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var Div = document.getElementById("CameraDiv");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var mCamera = Widget.Multimedia.Camera;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; mCamera.setWindow(Div);<br>
 *
 * @param {Object} DomObject a DOM object which specifites the  preview window.
 *
 * @returns
 * @type void
 */
Camera.prototype.setWindow = function(DomObject) {
	var objX = DomObject.offsetLeft;
	var objY = DomObject.offsetTop;
	var objWidth = DomObject.clientWidth;
	var objHeight = DomObject.clientHeight;
	CameraCaptureJava.setWindow(objX, objY, objWidth, objHeight);
};
/**
 * Start video capture. <br> 
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.Camera.onCameraCaptured = myCallBack;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallBack(String fileName) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;var v = fileName;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.Camera.startVideoCapture("a.3gp", false, 30, true);
 * 
 * @param {String} fileName the full path where the captured image to be saved. 
 * If filename includes a fullPath, then image file is saved to that exact location. 
 * @param {Boolean} lowRes the flag to specify if low resolution is used. 
 * @param {Number} maxDurationSeconds the max capture time. The unit is in second. 
 * This field should be an interger value.
 * @param {Boolean} showDefaultControls the flag to specify whether to show 
 * default controls.
 * @returns
 * @type void
 */
Camera.prototype.startVideoCapture = function(fileName,lowRes,maxDurationSeconds,showDefaultControls) {
	CameraCaptureJava.startVideoCapture(fileName,lowRes,maxDurationSeconds,showDefaultControls);
};

/**
 * Explicitly stop a video capture in process. <br> 
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.Camera.startVideoCapture("a.3gp", false, 30, true);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.Camera.stopVideoCapture();
 * 
 * @returns
 * @type void
 */
Camera.prototype.stopVideoCapture = function() {
	CameraCaptureJava.stopVideoCapture();
};

/**
 * Callback function when the camera stops capturing images or video. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Camera.onCameraCaptured = myCallBack;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallBack(String fileName) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;var v = fileName; <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Multimedia.Camera.captureImage("/sdcard/a.jpg", false);<br>
 *
 * @param {String} fileName full path of the saved file.
 *
 * @returns
 * @type void
 */
Camera.prototype.onCameraCaptured = function(fileName) {
}


//------------------------------------
//*********Config.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to be used for listing all functions and properties of 
 * JavaScript objects: Config.
 *
 * @version 0.1 
 */
Config.prototype = new Object();

/**
 * @class Config<br>
 * <br>
 * Config is a sub-object of <I>DeviceStateInfo</I>.<br>
 */
function Config() {
};

/**
 * Set Default Ringtone. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Device.DeviceStateInfo.Config.setDefaultRingtone("/sdcard/myringtone.mp3");
 * 
 * @param {String} ringtoneFileurl The ringtone file url
 * 
 * @returns
 * @type void
 */
Config.prototype.setDefaultRingtone = function(ringtoneFileurl) {
	ConfigJava.setDefaultRingtone(ringtoneFileurl);
};

/**
 * Set a picture as Wallpaper. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Device.DeviceStateInfo.Config.setAsWallpaper("/sdcard/mypic.jpg");
 * 
 * @param {String} wallpaperFileurl The wallpaper file url
 * 
 * @returns
 * @type void  
 */
Config.prototype.setAsWallpaper = function(wallpaperFileurl) {
	ConfigJava.setAsWallpaper(wallpaperFileurl);
};

/**
 * The volume of the ringtone. Possible values are from 0 to 7.
 * <I>getMsgRingtoneVolume</I>.
 *
 * @type Number
 */
Config.prototype.getringtoneVolume = function(){
    return ConfigJava.getringtoneVolume();
};

Config.prototype.setringtoneVolume = function(ringtoneVolume){
    ConfigJava.setringtoneVolume(ringtoneVolume);
};

/**
 * The volume of the ringtone to be played when a new message arrives. 
 * Possible values are from 0 to 7, where 0 is off, and 
 * 
 * @type Number
 */
Config.prototype.getmsgRingtoneVolume = function(){
    return ConfigJava.getmsgRingtoneVolume();
};

Config.prototype.setmsgRingtoneVolume = function(msgRingtoneVolume){
    ConfigJava.setmsgRingtoneVolume(msgRingtoneVolume);
};

/**
 * The status indicating whether the vibration is set or not. 
 * Possible values are "ON" and "OFF".
 * 
 * @type String
 */
Config.prototype.getvibrationSetting = function(){
    return obj2Str(ConfigJava.getvibrationSetting());
};

Config.prototype.setvibrationSetting = function(vibrationSetting){
    ConfigJava.setvibrationSetting(vibrationSetting);
};


//------------------------------------
//*********DataNetworkInfo.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to list all functions and properties of 
 * JavaScript object: DataNetworkInfo.
 * 
 * @version 0.1 
 */
DataNetworkInfo.prototype = new Object();

/**
 * @class DataNetworkInfo is a sub-object of <I>Widget.Device</I>.<br>
 */
function DataNetworkInfo() {
};

/**
 * Indicates whether the phone is connected to data network.
 * 
 * @type Boolean
 */
DataNetworkInfo.prototype.getisDataNetworkConnected = function(){
    return DataNetworkInfoJava.getisDataNetworkConnected();
};

/**
 * The array propery contains the types of the network connections to which the handset is currently connecting. 
 * e.g. "radio", "wifi", "bluetooth", "irda".
 *
 * @type Array
 */
DataNetworkInfo.prototype.getnetworkConnectionType = function(){
    return obj2Json(DataNetworkInfoJava.getnetworkConnectionType());
};

/**
 * Call back method to invoke when the phone connects to another data network. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Device.DataNetworkInfo.onNetworkConnectionChanged = myCallBack;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallBack(newConnectionName) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}
 * 
 * @param {String} newConnectionName name of the new network connection that the
 *            phone connects to.
 * 
 * @returns 
 * @type void
 */
DataNetworkInfo.prototype.setonNetworkConnectionChanged = function(callback){
	if(typeof callback !="function"){
		DataNetworkInfoJava.setonNetworkConnectionChanged(false);
	}else{
		this.onNetworkConnectionChanged=callback;
		DataNetworkInfoJava.setonNetworkConnectionChanged(true);
	}
};
DataNetworkInfo.prototype.onNetworkConnectionChanged = function(newConnectionName) {
};

/**
 * Retrieves the network connection name, specified by the type of the connection. E.g. "linksys", "WAP Gateway".
 *
 * @param {String} networkConnecionType the type of the network connection, see <I>DataNetworkInfo.networkConnectionType</I>.
 *
 * @returns the name of the network connection, if the specified connect is not existed, return null.
 * @type String  
 */

DataNetworkInfo.prototype.getNetworkConnectionName =function(networkConnecionType) {
	return obj2Str(DataNetworkInfoJava.getNetworkConnectionName(networkConnecionType));
};


//------------------------------------
//*********DeviceInfo.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to list all functions and properties of 
 * JavaScript object: DeviceInfo. <br>
 *
 * @version 0.1 
 */
DeviceInfo.prototype = new Object();

/**
 * @class DeviceInfo is a sub-object of <I>Widget.Device</I>. <br>
 * <br>
 * This represents static information about the host device. This is read-only 
 * information that does not change during runtime.<br>  
 * <br>
 * This is in contrast to the <I>DeviceStateInfo</I> object, which provides information 
 * about a phone that can change. E.g., available memory and keypad light status.<br>
 */
function DeviceInfo() {
};

/**
 * This is the information of the handset's owner which contains the same information as
 * other address book items.
 * 
 * @type {AddressBookItem}
 */
// Can't Finish
DeviceInfo.prototype.ownerInfo = function(){
    return new AddressBookItem();
};

/**
 * This is the color depth of the primary screen in its primary mode of operation.
 * 
 * @type Number
 */
DeviceInfo.prototype.getphoneColorDepthDefault = function(){
    return DeviceInfoJava.getphoneColorDepthDefault();
};

/**
 * This is the firmware version for the operating system.
 * 
 * @type String
 */
DeviceInfo.prototype.getphoneFirmware = function(){
    return obj2Str(DeviceInfoJava.getphoneFirmware());
};

/**
 * This is the manufacturer of the phone. E.g. "Nokia".
 * 
 * @type String
 */
DeviceInfo.prototype.getphoneManufacturer = function(){
    return obj2Str(DeviceInfoJava.getphoneManufacturer());
};

/**
 * This is the model of the phone. E.g., "N95".
 * 
 * @type String
 */
DeviceInfo.prototype.getphoneModel = function(){
    return obj2Str(DeviceInfoJava.getphoneModel());
};

/**
 * This is the operating system of the phone. E.g., "Windows Mobile 6.0".
 * 
 * @type String
 */
DeviceInfo.prototype.getphoneOS = function(){
    return obj2Str(DeviceInfoJava.getphoneOS());
};

/**
 * This is the software version of the phone.
 * 
 * @type String
 */
DeviceInfo.prototype.getphoneSoftware = function(){
    return obj2Str(DeviceInfoJava.getphoneSoftware());
};

/**
 * This is the pixel height of the primary screen in its primary mode of operation.
 * 
 * @type Number
 */
DeviceInfo.prototype.getphoneScreenHeightDefault = function(){
    return DeviceInfoJava.getphoneScreenHeightDefault();
};

/**
 * This is the pixel width of the primary screen in its primary mode of operation.
 * 
 * @type Number
 */
DeviceInfo.prototype.getphoneScreenWidthDefault = function(){
    return DeviceInfoJava.getphoneScreenWidthDefault();
};

/**
 * This is the total amount of RAM in kilobytes installed on the phone. This field 
 * should be double value.
 * 
 * @type Number
 */
DeviceInfo.prototype.gettotalMemory = function(){
    return DeviceInfoJava.gettotalMemory();
};




//------------------------------------
//*********DeviceStateInfo.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to be used for listing all functions and properties of 
 * JavaScript objects: DeviceStateInfo.
 *
 * @version 0.1 
 */
DeviceStateInfo.prototype = new Object();

/**
 * @class DeviceStateInfo provides device state info.<br>
 */
function DeviceStateInfo() {
};

/**
 * This issues an asynchronous request for the phone's location. <br>
 * This is an asynchronous call. The widget must register with the 
 * <I>onPositionRetrieved</I> callback method in order to receive the position information 
 * once it is available.<br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Device.DeviceStateInfo.requestPositionInfo();   
 * 
 * @param {String} method specifies how to get the position information, possible 
 * values are "cellid", "gps" and "agps"
 * 
 * @returns
 * @type void  
 */
DeviceStateInfo.prototype.requestPositionInfo = function(method) {
};

/**
 * This is a callback function that is invoked as a response to the requestLocationInfo() 
 * method. No object will be returned if the location could not be identified.<br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Device.DeviceStateInfo.onPositionRetrieved = myCallback;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallback(locationInfo, method) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}
 * 
 * @param {Positioninfo} locationInfo The location info.
 * @param {String} method specifies how the position information has been obtained, 
 * possible values are "cellid", "gps" and "agps".
 * 
 * @returns
 * @type void  
 */
DeviceStateInfo.prototype.onPositionRetrieved = function(locationInfo, method) {
};

/**
 * The amount of RAM in KB, that is available on the phone. This field should be a 
 * double value.
 * 
 * @type Number
 */
DeviceStateInfo.prototype.getavailableMemory = function(){
    return DeviceStateInfoJava.getavailableMemory();
};

/**
 * The language that is currently in use on the hand set. This property is read only. 
 * This is an ISO 639.2 value (http://www.loc.gov/standards/iso639-2/php/English_list.php).
 * 
 * @type String
 */
DeviceStateInfo.prototype.getlanguage = function(){
    return obj2Str(DeviceStateInfoJava.getlanguage());
};

/**
 * The Config object to change some of the device's configuration settings.
 * 
 * @type Config
 */
DeviceStateInfo.prototype.Config = new Config();

/**
 * This callback function is invoked when the phone's display changes characteristics. 
 * E.g., this would be called if the phone were to switch from landscape to portrait mode.<br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Device.DeviceStateInfo.onScreenChangeDimensions = myCallback;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallback(newWidth, newHeight) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}
 *
 * @param {Number} newWidth The new screen width. This field should be an integer value.
 * @param {Number} newHeight The new screen height. This field should be an integer value.
 *
 * @returns
 * @type void  
 */
DeviceStateInfo.prototype.setonScreenChangeDimensions = function(callback){
	if(typeof callback !="function"){
		DeviceStateInfoJava.setonScreenChangeDimensions(false);
	}else{
		this.onFlipEvent=callback;
		DeviceStateInfoJava.setonScreenChangeDimensions(true);
	}
}
DeviceStateInfo.prototype.onScreenChangeDimensions = function(newWidth, newHeight) {
};

/**
 * This is a callback event which is triggered when a clamshell opens or closes. 
 * The received parameter indicates if the clamshell has been closed.<br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Device.DeviceStateInfo.onFlipEvent = myCallback;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallback(isNowClosed) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}
 *
 * @param {Boolean} isNowClosed The state of Flip.
 *
 * @returns
 * @type void  
 */
DeviceStateInfo.prototype.setonFlipEvent = function(callback){
	if(typeof callback !="function"){
		DeviceStateInfoJava.setonFlipEvent(false);
	}else{
		this.onFlipEvent=callback;
		DeviceStateInfoJava.setonFlipEvent(true);
	}
}
DeviceStateInfo.prototype.onFlipEvent = function(isNowClosed) {
};

/**
 * A read-only property indicating whether the keypad light is on or off.
 *
 * @type Boolean
 */
DeviceStateInfo.prototype.getkeypadLightOn = function(){
    return DeviceStateInfoJava.getkeypadLightOn();
};

/**
 * A read-only property indicating whether the back light is on or off.
 *
 * @type Boolean
 */
DeviceStateInfo.prototype.getbackLightOn =  function(){
    return DeviceStateInfoJava.getbackLightOn();
};

/**
 * This is the percent utilization of the phone's processor. E.g., if the processor 
 * has a 20% load (80% idle) then the value will be 20.<br>
 * If the phone has multiple processors, then this value will be an average for all 
 * processors. 
 *
 * @type Double
 */
DeviceStateInfo.prototype.getprocessorUtilizationPercent = function(){
    return DeviceStateInfoJava.getprocessorUtilizationPercent();
};
 
/**
 * This property indicates where audio is currently configured for output. Valid 
 * values include "speaker", "receiver", "earphone".
 *
 * @type String
 */
DeviceStateInfo.prototype.getaudioPath = function(){
    return obj2Str(DeviceStateInfoJava.getaudioPath());
};

/**
 * The accelerometer information of the phone.
 *
 * @type AccelerometerInfo
 */
DeviceStateInfo.prototype.AccelerometerInfo = new AccelerometerInfo();

//------------------------------------
//*********File.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to list all functions and properties of 
 * JavaScript object: File.
 *  
 * @version 0.1 
 */
File.prototype = new Object();

/**
 * @class File is a sub-object of <I>Widget.Device</I>.<br>
 *
 */
function File() {
};
//Widget.Device.File.prototype=new File();
/**
 * The Date on which the file was first created. If the host operating system does not 
 * support this value, then the value will be the same as that of lastModifyDate. If 
 * neither createDate nor lastModifyDate is supported, then the value will be undefined.
 * 
 * @type Date
 */
File.prototype.createDate = undefined;

/**
 * Property indicating the name of the file. This does not include path information. 
 * E.g., "readme.txt"
 *
 * @type String
 */
File.prototype.fileName = undefined;

/**
 * This is the full path to the file on the file system. The value does not include the 
 * file name. E.g., "/local/data/".<br>
 * Note that the right slash (/) is adopted as a directory separator and will be supported 
 * by the widget runtime regardless of the underlying operating system.
 * 
 * @type String
 */
File.prototype.filePath = undefined;

/** 
 * The size of the file in bytes.
 * 
 * @type Number
 */
File.prototype.fileSize = undefined;  	 
 
/** 
 * This is a boolean indicating whether the file is a directory. If it is a directory, 
 * then the file size is 4096.
 * 
 * @type Boolean
 */
File.prototype.isDirectory = undefined;

/** 
 * The Date at which the file was last modified. If the host operating system does not 
 * support this value, then the value will be the same as that of createDate. If neither 
 * createDate nor lastModifyDate is supported, then the value will be undefined.
 * 
 * @type Date
 */
File.prototype.lastModifyDate = undefined ;

//------------------------------------
//*********Message.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to be used for listing all functions and properties of 
 * JavaScript objects: Message.
 *
 * @version 0.1 
 */
Message.prototype = new Object();

/**
 * @class Message is a class which represents a message. It provides important
 * information about a message.<br>
 *         
 * @constructor Construct a new Message object.
 */
function Message() {
	this.attachments = null;
	this.attachments = new Array();

	this.destinationAddress = null;
	this.destinationAddress = new Array();

	this.bccAddress = null;
	this.bccAddress = new Array();

	this.ccAddress = null;
	this.ccAddress = new Array();

    this.deliveryReport = false;
    this.readReport = false;
};

/**
 * The Id of this Message.
 * 
 * @type String
 */
Message.prototype.messageId = "";

/**
 * The callback number of this Message.
 * 
 * @type String
 */
Message.prototype.callbackNumber = "";

/**
 * The destination of this Message. This is an array of String.
 * 
 * @type Array
 */
Message.prototype.destinationAddress = new Array();

/**
 * The flag "read" for this Message.
 * 
 * @type Boolean
 */
Message.prototype.isRead = new Boolean();

/**
 * The priority of this Message.
 * 
 * @type Boolean
 */
Message.prototype.messagePriority = null;

/**
 * The type of this Message.
 * 
 * @type String
 */
Message.prototype.messageType = "";

/**
 * The subject of this Message.
 * 
 * @type String
 */
Message.prototype.subject = "";

/**
 * The body of this Message.
 * 
 * @type String
 */
Message.prototype.body = "";

/**
 * The source address of this Message. 
 *
 * @type String
 */
Message.prototype.sourceAddress = "";

/**
 * The validityPeriodHours of this Message. <br>
 * <br>
 * For SMS, the valid value for this attribute is:<br>
 * validityPeriodHours = 1  VALIDITY_PERIOD_1_HOUR<br>
 * validityPeriodHours = 2  VALIDITY_PERIOD_12_HOUR<br>
 * validityPeriodHours = 3  VALIDITY_PERIOD_1_DAY<br>
 * validityPeriodHours = 4  VALIDITY_PERIOD_1_WEEK<br>
 * validityPeriodHours = 5  ALIDITY_PERIOD_UNLIMITED 
 *
 * @type Number 
 */
Message.prototype.validityPeriodHours = new Number();

/**
 * The time of this Message.
 * 
 * @type Date
 */
Message.prototype.time = new Date();

/**
 * Weather the message need delivery report. This is a boolean.
 * 
 * @type Boolean
 */
Message.prototype.deliveryReport = new Boolean();

/**
 * Weather the message need read report. This is a boolean.
 * 
 * @type Boolean
 */
Message.prototype.readReport = new Boolean();

/**
 * The Cc Address of this Message. This is an array of String.
 * 
 * @type Array
 */
Message.prototype.ccAddress = new Array();

/**
 * The Bcc Address of this Message. This is an array of String.
 * 
 * @type Array
 */
Message.prototype.bccAddress = new Array()

/**
 * The attachments of this Message. This is an array of Attachment.
 * 
 * @type Array
 */
Message.prototype.attachments = new Array();

/**
 * Add a file to a Message as an attachment. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var msg = Widget.Messaging.createMessage(Widget.Messaging.MessageTypes.MMSMessage);<br> 
 * &nbsp;&nbsp;&nbsp;&nbsp;msg.addAttachment("/sdcard/sun.jpg");
 *
 * @param {String} fileFullName the full name of the file to be added as an attachment.
 * 
 * @returns
 * @type void
 */
Message.prototype.addAttachment = function(fileFullPath,mimeType) {
    var attach =  new Attachment();
    attach.filePath = fileFullPath;
    attach.MIMEType = mimeType;
    attach.fileName = fileFullPath;
    this.attachments.push(attach);
};

/**
 * Delete an attachment from the Message. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var msg=Widget.Messaging.createMessage(Widget.Messaging.MessageTypes.MMSMessage);<br> 
 * &nbsp;&nbsp;&nbsp;&nbsp;msg.addAttachment("/sdcard/sun.jpg");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;msg.addAttachment("/sdcard/moon.jpg");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var att = msg.attachments;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;msg.deleteAttachment(att[0]);
 *
 * @param {Attachment} attachment the attachment to be delete from Message.
 * 
 * @returns
 * @type void
 */
Message.prototype.deleteAttachment = function(attachment) {
    var len = this.attachments.length;
    var index = -1;
    for(var i=0;i<len;i++){
        var attach = this.attachments[i] ;
        if((attach.filePath == attachment.filePath)&&(attach.MIMEType == attachment.MIMEType)) {
            index = i;
        }
    }
    if(index != -1) {
        this.attachments.splice(index, 1);
    }
};


/**
 * Save an attachment in the message. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Message.saveAttachment("/sdcard/sun.jpg", attachment);<br>
 *
 * @param {String} fileFullName  the full destination file name.
 * @param {Attachment} attachment - the attachment to be saved.
 *
 * @returns
 * @type void
 */
Message.prototype.saveAttachment = function(fileFullName, attachment) {
};

/**
 * Add an address for the message. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var msg = Widget.Messaging.createMessage(Widget.Messaging.MessageTypes.SMSMessage);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;msg.addAddress("destination", "10086")<br>
 *
 * @param {String} type type of the address to be added. Possible values are "destination", "cc" and "bcc".
 * @param {String} address Address to be added to the destination address list.
 *
 * @returns
 * @type void
 */
Message.prototype.addAddress = function(type, address) {
    if(type == "destination") {
        var index = -1;
        var len = this.destinationAddress.length;
        for(var i=0;i<len;i++){
            var number = this.destinationAddress[i] ;
            if(number == address) {
                index = i;
            }
        } 
        if(index == -1) {
            this.destinationAddress.push(address);
        }
            
    }
}

/**
 * Delete an address from the message's destination addresses. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var msg=Widget.Messaging.createMessage(Widget.Messaging.MessageTypes.SMSMessage);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;msg.addAddress("cc", "10086")<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;msg.addAddress("cc", "13800138000")<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;msg.deleteAddress("cc", "10086")<br>
 *
 * @param {String} type type of the address to be added. Possible values are "destination", "cc" and "bcc".
 * @param {String} address Address to be deleted from the destination address list. 
 *
 * @returns
 * @type void
 */
Message.prototype.deleteAddress = function(type, address) {
    if(address == "destination") {
        var len = this.destinationAddress.length;
        var index = -1;
        for(var i=0;i<len;i++){
            var number = this.destinationAddress[i] ;
            if(number == address) {
                index = i;
            }
        }
        if(index != -1) {
            this.destinationAddress.splice(index, 1);
        }
    }
}

Message.prototype.toJSONString = function() {
    var len = this.destinationAddress.length;
    var deses = {};
    for(var i=0;i<len;i++){
        deses[i] = this.destinationAddress[i];
    } 
    
    len = this.attachments.length;
    var attachs = {}
    for(var i=0;i<len;i++){
        
        var attach = this.attachments[i];
        var s = attach.filePath+"<";
        s = s+attach.MIMEType+"<";
        s = s+attach.fileName;
        attachs[i] = s;
    }
    
    var msg = {};
    msg["messageId"] = this.messageId;
    msg["callbackNumber"] = this.callbackNumber;
    msg["isRead"] = this.isRead;
    msg["messageType"] = this.messageType;
    msg["subject"] = this.subject;
    msg["deliveryReport"] = this.deliveryReport ? "true" : "false";
    msg["readReport"] = this.readReport ? "true" : "false";
    msg["body"] = this.body;
    msg["destinationAddresses"] = jsobj2Str_acc(deses);
    msg["attachments"] = jsobj2Str_acc(attachs);
    
    return jsobj2Str(msg);
}





//------------------------------------
//*********MessageFolderTypes.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to be used for listing all functions and properties of 
 * JavaScript objects: MessageFolderTypes.
 *
 * @version 0.1 
 */
MessageFolderTypes.prototype = new Object();

/**
 * @class MessageFolderTypes contains common values that are referenced by the 
 * FolderType parameter of various MessagingService functions. This is not necessarily 
 * inclusive since custom folders can be defined.<br>
 * 
 * @constructor
 */
function MessageFolderTypes() {
};

/**
 * The drafts folder. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var msgFolderType = Widget.Messaging.MessageFolderTypes.DRAFTS;
 * 
 * @type String
 */
MessageFolderTypes.prototype.DRAFTS = "drafts";

/**
 * The inbox folder. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var msgFolderType = Widget.Messaging.MessageFolderTypes.INBOX;
 * 
 * @type String
 */
MessageFolderTypes.prototype.INBOX = "inbox";

/**
 * The outbox folder. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var msgFolderType = Widget.Messaging.MessageFolderTypes.OUTBOX;
 * 
 * @type String
 */
MessageFolderTypes.prototype.OUTBOX = "outbox";


//------------------------------------
//*********MessageQuantities.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to be used for listing all functions and properties of 
 * JavaScript objects: MessageQuantities.
 *
 * @version 0.1 
 */
MessageQuantities.prototype = new Object();

/**
 * @class MessageQuantities contains various message quantities that would apply to Folder 
 * for a message type. This is returned by the Messaging.getMessageQuantities method.<br>
 * 
 * @constructor
 */
function MessageQuantities() {
};

/**
 * Total found messages returned by query. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var msgType = Widget.Messaging.MessageTypes.SMSMessage;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var folderType = Widget.Messaging.MessageFolderTypes.INBOX;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var v = Widget.Messaging.getMessageQuantities(msgType, folderType);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var totalSMSCount = v.totalMessageCnt;
 * 
 * @type Number
 */
MessageQuantities.prototype.totalMessageCnt = new Number();

/**
 * Total read messages returned by query.
 * 
 * @type Number
 */
MessageQuantities.prototype.totalMessageReadCnt = new Number();

/**
 * Total unread messages returned by query.
 * 
 * @type Number
 */
MessageQuantities.prototype.totalMessageUnreadCnt = new Number();


//------------------------------------
//*********MessageTypes.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to be used for listing all functions and properties of 
 * JavaScript objects: MessageTypes.
 *
 * @version 0.1 
 */
MessageTypes.prototype = new Object();

/**
 * @class MessageTypes contains values that are referenced by the message type parameter 
 * in various Messaging service functions.<br>
 * 
 * @constructor
 */
function MessageTypes() {
};

/**
 * Identifier for Email Message Types. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var msgType = Widget.Messaging.MessageTypes.EmailMessage;
 * 
 * @type String
 */
MessageTypes.prototype.EmailMessage = "email";

/**
 * Identifier for MMS Message Types. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var msgType = Widget.Messaging.MessageTypes.MMSMessage;
 * 
 * @type String
 */
MessageTypes.prototype.MMSMessage = "mms";

/**
 * Identifier for SMS Message Types. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var msgType = Widget.Messaging.MessageTypes.SMSMessage;
 * 
 * @type String
 */
MessageTypes.prototype.SMSMessage = "sms";


//------------------------------------
//*********Messaging.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to be used for listing all functions and properties of 
 * JavaScript objects: Messaging.
 *
 * @version 0.1 
 */
Messaging.prototype = new Object();

/**
 * @class Messaging is the container object for JIL Messaging objects. <br>
 * <br>
 * Messaging is a sub-object of <I>Widget</I>. So we use Messaging in this way: 
 * Widget.Messaging.XXX<br>
 */
function Messaging() {
};
//Widget.Messaging.prototype=new Messaging();
/**
 * Create a message. It can be a sms, mms or email message. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * var msg = Widget.Messaging.createMessage(Widget.Messaging.MessageTypes.SMSMessage);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * document.getElementById("div1").innerHTML = msg.messageType;
 * 
 * @param {String} messageType the type of the message, The possible values are: "sms", "mms", "email".
 * 
 * @returns a {@link Message} object.
 * @type {@link Message}
 */
Messaging.prototype.createMessage = function(messageType) {
	var s = obj2Json(MessagingJava.createMessage(messageType));
    var m = new Message();
    m.messageType = s["messageType"];
    return m;
};

/**
 * Delete all messages by specified <i>messageType</i> and
 * <i>folderType</i> parameters. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * Widget.Messaging.deleteAllMessages(Widget.Messaging.MessageTypes.SMSMessage, Widget.Messaging.MessageFolderTypes.INBOX);
 * 
 * @param {String} messageType the type of the message, the possible values are: "sms", "mms", "email".
 * @param {String} folderType the name of the folder.
 * 
 * @returns
 * @type void
 */
Messaging.prototype.deleteAllMessages = function(messageType, folderType) {
    MessagingJava.deleteAllMessages(messageType,folderType);
};

/**
 * Delete a message with specified MessageType, FolderType, and message id. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * var msg = Widget.Messaging.getMessage(Widget.Messaging.MessageTypes.SMSMessage, Widget.Messaging.MessageFolderTypes.INBOX, 3);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; 
 * Widget.Messaging.deleteMessage(Widget.Messaging.MessageTypes.SMSMessage, Widget.Messaging.MessageFolderTypes.INBOX, msg.messageId);
 * 
 * @param {String} messageType the type of the message, the possible values are: "sms", "mms", "email".
 * @param {String} folderType the name of the folder.
 * @param {String} id the message id to be deleted.
 * 
 * @returns
 * @type void
 */
Messaging.prototype.deleteMessage = function(messageType, folderType, id) {
    MessagingJava.deleteMessage(messageType, folderType, id);
};

/**
 * Return a message for the specified MessageType, FolderType, and index value. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * var msg = Widget.Messaging.getMessage(Widget.Messaging.MessageTypes.SMSMessage, Widget.Messaging.MessageFolderTypes.INBOX, 3);<br>
 *
 * @param {String} messageType the type of the message, the possible values are: "sms", "mms", "email".
 * @param {String} folderType the name of the folder.
 * @param {Number} index the index of message. This field should be an interger value, starting from 0.    
 *
 * @returns A {@link Message} objects. null value will be returned if get the message failed
 * @type {@link Message}
 */

Messaging.prototype.getMessage = function(messageType, folderType, index) {
	var s = obj2Json(MessagingJava.getMessage(messageType, folderType, index));
	var m = new Message();
    if ((messageType == "sms")||(messageType == "mms")) {
        m.messageType = s["messageType"];
        m.messageId = s["messageId"];
        m.body = s["body"];
        m.subject = s["subject"];
        m.deliveryReport = s["deliveryReport"];
        m.readReport = s["readReport"];
        m.time = s["time"];
        m.isRead = s["isRead"];
        var deses = obj2Json(s["destinationAddresses"]);
        m.addAddress("destination",deses[0]);
    } 
	return m;
};

/**
 * Query how many messages of a message type are present in the specified folder. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * var v = Widget.Messaging.getMessageQuantities(Widget.Messaging.MessageTypes.SMSMessage, 
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
 * Widget.Messaging.MessageFolderTypes.INBOX);
 *
 * @param {String} messageType the type of the message, the possible values are: "sms", "mms", "email".
 * @param {String} folderType the type of the folder.
 *
 * @returns a {@link MessageQuantities} object
 * @type {@link MessageQuantities}
 */
Messaging.prototype.getMessageQuantities = function(messageType, folderType) {
	return new MessageQuantities();
};

/**
 * Attempt to send the specified message. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var msg = Widget.Messaging.createMessage(Widget.Messaging.MessageTypes.SMSMessage);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;msg.addAddress("destination","10086");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Messaging.sendMessage(msg);
 * 
 * @param {Message} msg the message to be send
 * 
 * @returns
 * @type void
 */
Messaging.prototype.sendMessage = function(msg) {
    var json = msg.toJSONString();
    MessagingJava.sendMessage(json);
};

/**
 * Move a message to folder. This Method move a message to a folder specified by name.<br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * var msg = Widget.Messaging.getMessage(Widget.Messaging.MessageTypes.SMSMessage, Widget.Messaging.MessageFolderTypes.INBOX, 3);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * Widget.Messaging.moveMessageToFolder(msg, Widget.Messaging.MessageFolderTypes.DRAFTS);<br>
 *
 * @param {Message} msg the message to be moved
 * @param {String} folderType the destination folder that the message will be moved.  
 * 
 * @returns
 * @type void
 */
Messaging.prototype.moveMessageToFolder = function(msg, folderType) {
};

/**
 * Copy a message to folder. This Method copy a message to a folder specified by name.<br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * var msg = Widget.Messaging.getMessage(Widget.Messaging.MessageTypes.SMSMessage, Widget.Messaging.MessageFolderTypes.SENTBOX, 0);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * Widget.Messaging.copyMessageToFolder(msg, Widget.Messaging.MessageFolderTypes.DRAFTS);<br>
 *
 * @param {Message} msg the message to be copied.
 * @param {String} folderType the destination folder that the message will be copied to. 
 * 
 * @returns
 * @type void
 */
Messaging.prototype.copyMessageToFolder = function(msg, folderType) {
};

/**
 * Create a folder for specified message type. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * Widget.Messaging.createFolder(Widget.Messaging.MessageTypes.SMSMessage, "myfolder");<br>
 *
 * @param {String} messageType the type of the message. The possible values are: "sms", "mms", "email".
 * @param {String} folderName the name of the folder to be created.
 * 
 * @returns
 * @type void
 */
Messaging.prototype.createFolder = function(messageType, folderName) {
};

/**
 * Delete a folder from folders of specified message type. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * Widget.Messaging.deleteFolder(Widget.Messaging.MessageTypes.SMSMessage, "myfolder");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 *
 * @param {String} messageType the type of the message. The possible values are: "sms", "mms", "email".
 * @param {String} folderName the name of the folder to be deleted.
 * 
 * @returns
 * @type void
 */
Messaging.prototype.deleteFolder = function(messageType, folderName) {
};

/**
 * This is a callback function which will be triggered when a new message comes. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Messaging.onMessageArrived = function(msg) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;alert("new Message's coming from: "+msg.sourceAddress);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br>
 * 
 * @param {Message} msg the new incoming message 
 * 
 * @returns
 * @type void
 */
Messaging.prototype.setonMessageArrived = function(callback){
	if(typeof callback !="function"){
		MessagingJava.setonMessageArrived(false);
	}else{
		this.onMessageArrived=callback;
		MessagingJava.setonMessageArrived(true);
	}
};
Messaging.prototype.onMessageArrived = function(msg) {
};

Messaging.prototype.onMessageArrivedProxy = function(msg) {
    var s = obj2Json(msg);
	var m = new Message();
	var mesageType = s["messageType"]
    if ((messageType == "sms")||(messageType == "mms")) {
        m.messageType = messageType;
        m.messageId = s["messageId"];
        m.body = s["body"];
        m.subject = s["subject"];
        m.time = s["time"];
        m.isRead = s["isRead"];
        var deses = obj2Json(s["destinationAddresses"]);
        m.addAddress("destination",deses[0]);
    } 
	this.onMessageArrived(m);
};

/**
 * This is a callback function which will be triggled when send a message failed. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * Widget.Messaging.onMessageSendingFailure = function(msg, errCode) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * alert("send message failed, error code: "+errCode);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * }<br>
 * 
 * @param {Message} msg the message sending failed
 * @param {String} errcode possible values maybe: "1: No networking", "2: Unable to establish network connection", "3: Incompatible payload", "4: Unknown error".
 * 
 * @returns
 * @type void
 */
Messaging.prototype.onMessageSendingFailure = function(msg, errcode) {
};

/**
 * Return an array of the folder names available for the specified message type. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;
 * var folderNames = Widget.Messaging.getFolderNames(Widget.Messaging.MessageTypes.SMSMessage);
 * 
 * @param {String} messageType the type of the message, the possible values are: "sms", "mms", "email".
 * 
 * @returns An array of String objects.
 * @type Array
 */
Messaging.prototype.getFolderNames = function(messageType) {
	return new Array();
};


/**
 * This method search messages with designed condition in specified message box. 
 * This is an asynchronous function, and will invoke Messaging.onMessagesFound() 
 * when search completes. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var msg = Widget.Messaging.createMessage(Widget.Messaging.MessageTypes.SMSMessage);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;msg.addAddress("destination","10086");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Messaging.onMessagesFound = myCallBack;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Messaging.findMessages(msg, Widget.Messaging.MessageFolderTypes.INBOX, 0, 9);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallBack(messages) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;var size = messages.length;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *
 * @param {Message} comparisonMsg the comparison messaqge which defines the rules for finding.
 * @param {String} folderType the name of the folder. Possible values are "inbox", "outbox", "sentbox", "drafts" and "all".
 * @param {Number} startInx an integer value to specify the index the returning search results starts from
 * @param {Number} endInx an integer value to specify the index to which the returning search results ends at 
 *
 * @returns 
 * @type void
 */
Messaging.prototype.findMessages = function(comparisonMsg, folderType, startInx, endInx) {
};

/**
 * Callback method to invoke when Messaging.findMessages completes. <br>
 * Called asynchronously when the message send to the recipient failed due to mail format error or 
 * mail/smsc server rejected or network error.
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var msg = Widget.Messaging.createMessage(Widget.Messaging.MessageTypes.SMSMessage);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;msg.addAddress("destination","10086");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Messaging.onMessagesFound = myCallBack;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Messaging.findMessages(msg, Widget.Messaging.MessageFolderTypes.INBOX, 0, 9);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallBack(messages) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;alert("matched message number: "+ messages.length);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *
 * @param {Array} messagesFound An array of {@link Message} objects, null value will be returned if no matched messages
 * @param {String} folderType the name of the folder.
 *
 * @returns
 * @type void
 */
Messaging.prototype.onMessagesFound = function(messagesFound, folderType) {
};

/**
 * Get current Email account. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var account = Widget.Messaging.getCurrentEmailAccount();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;alert(account.accountName);<br>
 *
 * @returns Email account Object
 * @type {Account}
 */
Messaging.prototype.getCurrentEmailAccount = function(){
	return new Account();
};

/**
 * Get all Email accounts. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var account = Widget.Messaging.getEmailAccounts();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;alert("Email account number = "+account.length);<br>
 *
 * @returns Array of Email account Objects
 * @type Array
 */
Messaging.prototype.getEmailAccounts = function(){
	return new Array();
};

/**
 * Set the Email account specified by account ID as default Email account. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var account = Widget.Messaging.getEmailAccounts();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Messaging.setCurrentEmailAccount(account[0].accountId);<br>
 *
 * @param {String} AccountId Email account Id
 *
 * @returns
 * @type void
 */
Messaging.prototype.setCurrentEmailAccount = function(AccountId){
};

/**
 * Delete an Email account specified by account ID. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var account = Widget.Messaging.getEmailAccounts();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Messaging.deleteEmailAccount(account[1].accountId);<br>
 *
 * @param {String} AccountId Email account Id
 *
 * @returns
 * @type void
 */
Messaging.prototype.deleteEmailAccount = function(AccountId) {
};

/**
 * Email account. 
 * 
 * @type Account
 */
Messaging.prototype.Account = new Account();

/**
 * Attachment types. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var attachment = new Widget.Messaging.Attachment();<br>
 *
 * @type Attachment
 */
Messaging.prototype.Attachment = new Attachment();

/**
 * Message Object. 
 * 
 * @type Message
 */
Messaging.prototype.Message = new Message();

/**
 * Message folder types. 
 * 
 * @type MessageFolderTypes
 */
Messaging.prototype.MessageFolderTypes = new MessageFolderTypes();

/**
 * MessageQuantities types. 
 * 
 * @type MessageQuantities
 */
Messaging.prototype.MessageQuantities = new MessageQuantities();

/**
 * Message types. 
 * 
 * @type MessageTypes
 */
Messaging.prototype.MessageTypes = new MessageTypes();


//------------------------------------
//*********VideoPlayer.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to be used for listing all functions and properties of 
 * JavaScript objects: VideoPlayer.
 *
 * @version 0.1 
 */
VideoPlayer.prototype = new Object();

/**
 * @class VideoPlayer is the object for JIL Video play capabilities. <br>
 * <br>
 * VideoPlayer is a sub-object of <I>Multimedia</I>. We use VideoPlayer in this way:<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var mvplayer = Widget.Multimedia.VideoPlayer;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.xxxx<br>
 */

function VideoPlayer() {
};


/**
 * Open an video file which will be played by <i>play</i> function. <br> 
 * The widget engine is responsible for file caching, etc. The following protocols 
 * are supported by the open() function: "file://", "rtsp://","http://".<br>
 * If the parameter string doesn't start with the protocol syntax listed above, 
 * the widget engine will search the media files in the widget package.<br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var Div = document.getElementById("PlayDiv");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var mvplayer = Widget.Multimedia.VideoPlayer;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var mvplayer.setWindow(Div);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.open("file:///sdcard/Sample.mp4");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.open("Sample.mp4");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.open("http://www.sample.com/sample.mp4");
 *
 * @param {String} fileUrl the url of the Video file to be played.
 *
 * @returns
 * @type void
 */
VideoPlayer.prototype.open = function(fileUrl) {
	VideoPlayerJava.open(fileUrl);
};


/**
 * This method attaches the player to a DOM object which specifites the video <br>
 * playing window.
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var Div = document.getElementById("PlayDiv");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var mvplayer = Widget.Multimedia.VideoPlayer;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var mvplayer.setWindow(Div);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.open("file:///sdcard/Sample.mp4");<br>
 *
 * @param {Object} DomObject a DOM object which specifites the video playing window.
 *
 * @returns
 * @type void
 */
VideoPlayer.prototype.setWindow = function(DomObject) {
	var objX = DomObject.offsetLeft;
	var objY = DomObject.offsetTop;
	var objWidth = DomObject.clientWidth;
	var objHeight = DomObject.clientHeight;
	VideoPlayerJava.setWindow(objX, objY, objWidth, objHeight);
};

/**
 * Play the video which is opened by <i>open</i> function. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var Div = document.getElementById("PlayDiv");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var mvplayer = Widget.Multimedia.VideoPlayer;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var mvplayer.setWindow(Div);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.onStateChange= myCallback(state);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.open("file:///sdcard/Sample.mp4");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.setVideoScreen(width,higth);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallback(state) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if (state == "opened")<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;myPlayer.play(5);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *
 * @param {Number} repeatTimes an integer value specifies how many times the 
 * media file should be played.
 *
 * @returns
 * @type void
 */
VideoPlayer.prototype.play = function(repeatTimes) {
	VideoPlayerJava.play(repeatTimes);
};

/**
 * Pause the video which is played by <i>play</i> function. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var Div = document.getElementById("PlayDiv");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var mvplayer = Widget.Multimedia.VideoPlayer;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var mvplayer.setWindow(Div);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.open("file:///sdcard/Sample.mp4");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.setVideoScreen(width,higth);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.onStateChange= myCallback(state);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.play(5);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallback(state) { <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if (state == "playing") <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;myPlayer.pause(); <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;} <br>
 *
 * @returns
 * @type void
 */
VideoPlayer.prototype.pause = function() {
	VideoPlayerJava.pause();
};

/**
 * Resume the video which is paused by <i>pause</i> function. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var Div = document.getElementById("PlayDiv");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var mvplayer = Widget.Multimedia.VideoPlayer;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var mvplayer.setWindow(Div);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.open("file:///sdcard/Sample.mp4");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.onStateChange= myCallback(state);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.play(5);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.pause();
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallback(state) { <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if (state == "paused") <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;myPlayer.resume(); <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;} <br>
 *
 * @returns
 * @type void
 */
VideoPlayer.prototype.resume = function() {
	VideoPlayerJava.resume();
};

/**
 * Stop the video which is played by <i>playing</i> function. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var Div = document.getElementById("PlayDiv");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var mvplayer = Widget.Multimedia.VideoPlayer;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var mvplayer.setWindow(Div);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.open("file:///sdcard/Sample.mp4");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.setVideoScreen(width,higth);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.onStateChange= myCallback(state);<br>

 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.play(5);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallback(state) { <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if (state == "playing") <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;myPlayer.stop(); <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;} <br>
 *
 * @returns
 * @type void
 */
VideoPlayer.prototype.stop = function() {
	VideoPlayerJava.stop();
};

/**
 * Call back method to invoke when the state of the video player changes. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var Div = document.getElementById("PlayDiv");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var mvplayer = Widget.Multimedia.VideoPlayer;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var mvplayer.setWindow(Div);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.open("file:///sdcard/Sample.mp4");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.setVideoScreen(width,higth);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.onStateChange= myCallback(state);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;mvplayer.play(5);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallback(state) { <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if (state == "playing") <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;myPlayer.stop(); <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;} <br>
 *
 * @param {String} state indicating the current state of the video player. 
 * Possible values are "InitStatus", "opened", "stopped", "paused" and "playing".
 *
 * @returns
 * @type void
 */
VideoPlayer.prototype.onStateChange = function(state) {
};


//------------------------------------
//*********Multimedia.js   ***********
//------------------------------------

/**
 * @fileoverview This file is to be used for listing all functions and
 *               properties of JavaScript objects: Multimedia.
 * @version 0.1
 */
Multimedia.prototype = new Object();

/**
 * @class Multimedia is the container object for JIL Multimedia objects. <br>
 * <br>
 * Multimedia is a sub-object of <I>Widget</I>. Multimedia is accessed
 * directly from the object: Widget.Multimedia.XXX<br>
 * <br>
 * Support media format:<br>
 * Audio: MP3, AAC, AAC+ v1/v2, AMR-NB, AMR-WB<br>
 * Video: MPEG4, H.263, H.264<br>
 * Streaming: All above audio/video codec in 3GPP file formats<br>
 * Image: JPEG, PNG, GIF<br>
 */
function Multimedia() {
};

/**
 * The AudioPlayer object.
 * 
 * @type AudioPlayer
 */
Multimedia.prototype.AudioPlayer = new AudioPlayer();//

/**
 * The Camera object.
 * 
 * @type Camera
 */
Multimedia.prototype.Camera = new Camera();

/**
 * The VideoPlayer object.
 * 
 * @type VideoPlayer
 */
Multimedia.prototype.VideoPlayer = new VideoPlayer();
/**
 * Get audio volume. Value range is 0 - 15, where 0 is off. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var v = Widget.Multimedia.getVolume();
 * 
 * @returns the value of volume. This field should be an integer value.
 * @type Number
 */
 
Multimedia.prototype.getVolume = function() {
	var v = ConfigJava.getmusicMaxVolume();
	if (0 != v) {
		return ConfigJava.getmusicVolume() * 15 / v;
	}
	else {
		return 0;
	}
};


/**
 * Stop all audio and video play. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp; Widget.Multimedia.stopAll();
 * 
 * @returns
 * @type void
 */
Multimedia.prototype.stopAll = function() {
	Multimedia.prototype.VideoPlayer.stop();
	Multimedia.prototype.AudioPlayer.stop();
};

/**
 * The flag to check if there is any audio playing. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;if (Multimedia.isAudioPlaying) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Multimedia.AudioPlayer.pause();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}
 * 
 * @type Boolean
 */
Multimedia.prototype.getisAudioPlaying = function(){
    return AudioPlayerJava.isPlaying();
};

/**
 * The flag to check if there is any video playing. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;if (Multimedia.isVideoPlaying) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Multimedia.VideoPlayer.pause();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}
 * 
 * @type Boolean
 */
Multimedia.prototype.getisVideoPlaying = function(){
    return VideoPlayerJava.isPlaying();
};




//------------------------------------
//*********PositionInfo.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to list all functions and properties of 
 * JavaScript object: PositionInfo.
 * 
 * @version 0.1 
 */
PositionInfo.prototype = new Object();

/**
 * @class PositionInfo is the container object for JIL PositionInfo object. <br>
 * <br>
 * This object provides read-only information about the device's position information.
 * This information will be undefined if the back-end services should be unavailable 
 * (e.g., due to network unavailability) or if the Widget does not have sufficient 
 * security permissions to obtain this information. <br>
 *
 */
function PositionInfo() {
};

/**
 * Latitude in degrees using the World Geodetic System 1984 (WGS84) datum. This field 
 * should be a double value.
 * 
 * @type Number
 */
PositionInfo.prototype.latitude = undefined;

/**
 * Longitude in degrees using the World Geodetic System 1984 (WGS84) datum. This field 
 * should be a double value.
 * 
 * @type Number	
 */
PositionInfo.prototype.longitude = undefined;

/**
 * Altitude in meters using the World Geodetic System 1984 (WGS84) datum. This field 
 * should be a double value.
 * 
 * @type Number
 */
PositionInfo.prototype.altitude = undefined;
	
/**
 * This is the id of the cell. This field should be an interger value.
 * 
 * @type Number
 */
PositionInfo.prototype.cellID = undefined;

/**
 * The horizontal accuracy of the position in meters, or null if not available. 
 * This field should be a double value.
 *
 * @type Number
 */
PositionInfo.prototype.accuracy = undefined;

/**
 * The vertical accuracy of the position in meters, or null if not available. 
 * This field should be a double value.
 *
 * @type Number
 */
PositionInfo.prototype.altitudeAccuracy = undefined;

/**
 * The time when the location was established.
 *
 * @type Date
 */
PositionInfo.prototype.timeStamp = undefined;


//------------------------------------
//*********PowerInfo.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to list all functions and properties of 
 * JavaScript object: PowerInfo.
 *
 * @version 0.1 
 */
PowerInfo.prototype = new Object();

/**
 * @class PowerInfo is a sub-object of <I>Widget.Device</I>.<br>
 * <br>
 * This represents dynamic information of phone power. It is read-only.<br>
 * 
 */
function PowerInfo() {
};

/**
 * This field indicates whether the phone is now charging.
 * 
 * @type Boolean
 */
PowerInfo.prototype.getisCharging = function(){
    return PowerInfoJava.getisCharging();
};

/**
  * This field indicates the percentage of battery power remaining. This field 
  * should be an integer value.
  * 
  * @type Number
  */
PowerInfo.prototype.getpercentRemaining = function(){
    return PowerInfoJava.getpercentRemaining();
};

/**
 * Call back method to invoke when the charge state changes. 
 * 
 * @param {String} state string value of the phone's charging status. Possible values 
 * are "charging", "discharging" and "full".
 * 
 * @returns
 * @type void  
 */
PowerInfo.prototype.setonChargeStateChange = function(callback){
	if(typeof callback !="function"){
		PowerInfoJava.setonChargeStateChange(false);
	}else{
		this.onChargeStateChange=callback;
		PowerInfoJava.setonChargeStateChange(true);
	}
};
PowerInfo.prototype.onChargeStateChange = function(state) {
};

/**
 * Callback method to invoke when the battery is low. 
 *
 * @param {Number} percentRemaining integer value indicating the percentage of 
 * the remaining power.
 *
 * @returns
 * @type void  
 */
PowerInfo.prototype.setonLowBattery = function(callback){
	if(typeof callback !="function"){
		PowerInfoJava.setonLowBattery(false);
	}else{
		this.onLowBattery=callback;
		PowerInfoJava.setonLowBattery(true);
	}
};
PowerInfo.prototype.onLowBattery = function(percentRemaining) {
};

/**
 * Callback method to invoke when the percentage of the remaining battery changes 
 * during the charging process. 
 *
 * @param {Number} newPercentageRemaining integer value indicating the percentage 
 * of power remaining.
 *
 * @returns
 * @type void  
 */
PowerInfo.prototype.setonChargeLevelChange = function(callback){
	if(typeof callback !="function"){
		PowerInfoJava.setonChargeLevelChange(false);
	}else{
		this.onChargeLevelChange=callback;
		PowerInfoJava.setonChargeLevelChange(true);
	}
};
PowerInfo.prototype.onChargeLevelChange = function(newPercentageRemaining) {
};


//------------------------------------
//*********RadioInfo.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to list all functions and properties of 
 * JavaScript object: RadioInfo.
 * 
 * @version 0.1 
 */
RadioInfo.prototype = new Object();

/**
 * @class RadioInfo is a sub-object of <I>Widget.Device</I>.<br>
 * <br>
 * This represents dynamic information on phone radio. It is read-only.<br>
 */
function RadioInfo() {
};

/**
 * This field indicates whether the phone is roaming.
 * 
 * @type Boolean
 */
RadioInfo.prototype.getisRoaming = function(){
    return RadioInfoJava.getisRoaming();
};


/**
 * This field indicates the percentage of signal strength. This field should be an integer value.
 * 
 * @type Number
 */
RadioInfo.prototype.getradioSignalStrengthPercent =  function(){
    return RadioInfoJava.getradioSignalStrengthPercent();
};


/**
 * Call back method to invoke when the signal source changes. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Device.RadioInfo.onSignalSourceChange = myCallBack;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallBack(signalSource, isRoaming) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}
 *  
 * @param {Number} signalSource source of the signal  //maybe it is strength
 * @param {Boolean} isRoaming roaming status
 *
 * @returns
 * @type void  
 */
RadioInfo.prototype.setonSignalSourceChange = function(callback){
	if(typeof callback !="function"){
		RadioInfoJava.setonSignalSourceChange(false);
	}else{
		this.onSignalSourceChange=callback;
		RadioInfoJava.setonSignalSourceChange(true);
	}
};
RadioInfo.prototype.onSignalSourceChange = function(radioSignalStrengthPercent, isRoaming) {
};

/**
 * This field indicates whether the phone is allowed to connect to radio network.
 *
 * @type Boolean
 */
RadioInfo.prototype.getisRadioEnabled = function(){
    return RadioInfoJava.getisRadioEnabled();
};


/**
 * This field indicates the singal source of the radio network. Possible values 
 * include "gsm", "gprs", "edge", "e911", etc.
 *
 * @type String
 */
RadioInfo.prototype.getradioSignalSource = function(){
    return obj2Str(RadioInfoJava.getradioSignalSource());
};



//------------------------------------
//*********WidgetManager.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is used for listing all functions and properties of 
 * JavaScript object: WidgetManager.
 *
 * @version 0.1 
 */

/**
 * @class WidgetManager<br>
 */
function WidgetManager() {
};

 

/**
 * This method is used to check the installation status of a specific widget.
 * 
 * @param {String} The id of the widget to be checked.This value corresponds to value of the wid tag   
 * specified in the config.xml
 * @param {String} The name of the widget to be checked.This value corresponds to the vlaue of the name tag  
 * specified in the config.xml.
 * @param {String} The version of the widget to be checked.This value corresponds to the vlaue of the 
 * version tag specified in the config.xml.
 * 
 * @returns
 * @type String
 */
WidgetManager.prototype.checkWidgetInstallationStatus = function(widgetId, widgetName, widgetVersion) {
	return "";
};

window.WidgetManager = new WidgetManager();
 


//------------------------------------
//*********Device.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is to be used for listing all functions and properties of 
 * JavaScript objects: Device. 
 *  
 * @version 0.1 
 */
Device.prototype = new Object();

/**
 * @class Device provides access to the Device. <br>
 * <br>
 * All the file/directory functions can manipulate entire file system, except that 
 * some methods may fail as the permission of file or directoy. <br>
 * <br>
 * Usally, "/local" and "/sdcard" (if Micro SD card is inserted) directory can be used 
 * by most copy/delete/create functions.<br>
 */
function Device() {
};
//Widget.Device.prototype=new Device();
/**
 * This provides access to a File object located at the specified full path and name. 
 * This fullName should correspond to the <I>fullName</I> property of the file, which 
 * includes the path and the file name. A right slash ('/') is used as the directory 
 * separator. This method also applies to directories.<br> 
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var v = Widget.Device.getFile("/sdcard/usr/mydata");
 * 
 * @param {String} fullName The full file name
 * 
 * @returns File object as a reference of the specified file. A null value is returned 
 * if the file is not found at the specified location;
 * @type File
 */
Device.prototype.getFile = function(fullName) {
	return jsobj2File(obj2Json(DeviceJava.getFile(fullName)));
};

/**
 * Get array of names of all files in the specified directory. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var v = Widget.Device.getDirectoryFileNames("/sdcard/usr/");
 *    
 * @param {String} sourceDirectory The directory's full name to get files.
 * 
 * @returns Returns an array of the names for all files found in the specified path. 
 * An empty array is returned if no files are found. A null value is returned if the 
 * specified path is not valid.
 * @type Array
 */
Device.prototype.getDirectoryFileNames = function(sourceDirectory) {
	return obj2Json(DeviceJava.getDirectoryFileNames(sourceDirectory));
};

/**
 * This method moves the specified file to the path and file name specified in the 
 * destinationFullName. If success, the source file is removed from its original 
 * location and placed in the new location with the new file name. The new file's 
 * fullName property will be that specified by the destinationFullName value in 
 * this function.This method also applies to directories. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var v = Widget.Device.moveFile("/sdcard/usr/a", "/sdcard/usr/b");   
 * 
 * @param {String} originalFile The full name of file to be moved.
 * @param {String} destinationFullName The full name of destination file.
 * 
 * @returns A boolean is returned indicating the success of the operation.
 * @type Boolean  
 */
Device.prototype.moveFile = function(originalFile, destinationFullName) {
	return DeviceJava.moveFile(originalFile,destinationFullName);
};

/**
 * This method copies the specified file to the path and file name specified in the 
 * destinationFullName. The new file's fullName property will be that specified by 
 * the destinationFullName value in this function.This method also applies to directories. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var v = Widget.Device.copyFile("/sdcard/usr/a", "/sdcard/usr/b");
 *    
 * @param {String} originalFile The full name of file to be copied.
 * @param {String} destinationFullName The full name of destination file.
 * 
 * @returns A boolean is returned indicating the success of the operation.
 * @type Boolean  
 */
Device.prototype.copyFile = function(originalFile, destinationFullName) {
	return DeviceJava.copyFile(originalFile,destinationFullName);
};

/*
 * This method create the folder located at the specified fullName. The full name 
 * includes the path and folder name. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var v = Widget.Device.createFolder("/sdcard/usr/");
 * 
 * @param {String}
 *            destinationFullName The full name of folder to be created.
 * 
 * @returns A boolean is returned indicating the success of the operation.
 * @type Boolean

*/
Device.prototype.createFolder = function(destinationFullName) {
	return DeviceJava.createFolder(destinationFullName);
};

/*
 * This method delete the folder located at the specified fullName. The full name 
 * includes the path and folder name. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var v = Widget.Device.deleteFolder("/sdcard/usr/");
 * 
 * @param {String}
 *            destinationFullName The full name of folder to be deleted.
 * 
 * @returns A boolean is returned indicating the success of the operation.
 * @type Boolean

*/
Device.prototype.deleteFolder = function(destinationFullName) {
	return DeviceJava.deleteFolder(destinationFullName);
};
	
/*
 * This method moves the specified folder to the path and folder name specified
 * in the destinationFullName. If success, the source folder is removed from
 * its original location and placed in the new location with the new folder
 * name. The new folder's fullName property will be that specified by the
 * destinationFullName value in this function. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var v = Widget.Device.moveFolder("/sdcard/usr/a/", "/sdcard/usr/b/");
 * 
 * @param {String}
 *            originalFolder The full name of folder to be moved.
 * @param {String}
 *            destinationFolder The full name of destination folder.
 * 
 * @returns A boolean is returned indicating the success of the operation.
 * @type Boolean

*/
Device.prototype.moveFolder = function(originalFolder, destinationFolder) {
	return DeviceJava.moveFolder(originalFolder,destinationFolder);
};

/*
 * This method copies the specified folder to the path and folder name specified 
 * in the destinationFullName. The new folder's fullName property will be that 
 * specified by the destinationFullName value in this function. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var v=Widget.Device.copyFolder("/sdcard/usr/a/", "/sdcard/usr/b/");
 * 
 * @param {String}
 *            originalFolder The full name of folder to be copied.
 * @param {String}
 *            destinationFolder The full name of destination folder.
 * 
 * @returns A boolean is returned indicating the success of the operation.
 * @type Boolean

*/

Device.prototype.copyFolder = function(originalFolder, destinationFolder) {
	return DeviceJava.copyFolder(originalFolder,destinationFolder);
};
	
/**
 * This method create the file located at the specified full name. The full name
 * includes the path and file name. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var v = Widget.Device.createFile("/sdcard/usr/a");
 * 
 * @param {String}
 *            destinationFullName The full name of file to be created.
 * 
 * @returns A boolean is returned indicating the success of the operation.
 * @type Boolean
 */
Device.prototype.createFile = function(destinationFullName) {
	return DeviceJava.createFile(destinationFullName);
};

/**
 * This method deletes the file located at the specified fullName. The full name 
 * includes the path and file name. This method also applies to directories. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var v = Widget.Device.deleteFile("/sdcard/usr/a");
 *    
 * @param {String} destinationFullName The full name of file to be deleted.
 * 
 * @returns A boolean is returned indicating the success of the operation.
 * @type Boolean  
 */
Device.prototype.deleteFile = function(destinationFullName) {
	return DeviceJava.deleteFile(destinationFullName);
};


/**
 * This method returns an array of Files on all file systems that match the 
 * specified File. Widget can specify only search a range of files between 
 * startInx and endInx. 
 * <br>
 * This is an asynchronous function, and will invoke Device.onFilesFound() 
 * when search complete.<br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Device.onFilesFound = myCallBack;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var v = Widget.Device.findFiles(matchfile, 0, 9);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallBack(files) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *    
 * @param {File} matchFile The <i>File</i> object as a match file 
 * @param {Number} startInx an integer value to specify the index the returning 
 * search results starts from.
 * @param {Number} endInx an integer value to specify the index to which the returning 
 * search results ends at.
 * 
 * @returns
 * @type void
 */
Device.prototype.findFiles = function(matchFile, startInx, endInx) {
	DeviceJava.findFiles(jsobj2Str(matchFile), startInx, endInx);
};

/**
 * Callback method to invoke when Device.findFiles complete. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Device.onFilesFound = myCallBack;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var v = Widget.Device.findFiles(matchfile, 0, 9);<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallBack(files) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *    
 * @param {Array} filesFound an array of File objects
 * 
 * @returns 
 * @type void
 */
Device.prototype.onFilesFound = function(filesFound) {
};

Device.prototype.onFilesFoundproxy = function(filesFound) {
	var filesFoundproxy=obj2Json(filesFound);
	var i;
	for(i=0;i<filesFoundproxy.length;i++){
		filesFoundproxy[i]=jsobj2File(filesFoundproxy[i]);
	};
	this.onFilesFound(filesFoundproxy);
};


/**
 * This function is called to set Ringtone for an addressbook item. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Device.setRingtone("/sdcard/myringtone.mp3", addressItem);
 *  
 * @param {String} ringtoneFileurl The ringtone file url.
 * @param {AddressBookItem} addressBookItem The addressbook item to set the ringtone.
 * 
 * @returns
 * @type void
 */
Device.prototype.setRingtone = function(ringtoneFileurl, addressBookItem) {
};


/**
 * Get array of the names of applications that can be launched.  <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var v = Widget.Device.getAvailableApplications();
 * 
 * @returns An array of String of the application names. Possible value on the phone
 * may be "Dialer", "ProgMng","Sound Recorder","Sync", "FileMgr", "Browser","Monternet",
 * "Camera", "Picture", "Mobile TV" etc. This depends on what applications installed on 
 * phone
 *
 * @type Array
 */
Device.prototype.getAvailableApplications = function() {
	return obj2Json(DeviceJava.getAvailableApplications());
};

/**
 * Launch a specified native application with start parameters. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Device.launchApplication("Media Player", "/sdcard/a.mp4");
 *  
 * @param {String} application The application to be launched. The application identifer String could be
 * "Camera", "Media Player", "Sound Recorder" etc., which can be get through getAvailableApplications() API. 
 * @param {String} startParameter The startup parameter passed to application.
 * 
 * @returns
 * @type void
 */
Device.prototype.launchApplication = function(application, startParameter) {
	DeviceJava.launchApplication(application,startParameter);
};
/**
 * Launch a specified native application for result with start parameters. <br>
 *  
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Device.launchApplication("File", "/sdcard");
 *  
 * @param {String} application The application to be launched. The application identifer String could be
 * "Contact", "File", "Messaging" . 
 * @param {String} startParameter The startup parameter passed to application.
 * 
 * @returns
 * @type void
 */
Device.prototype.launchApplicationForResult = function(application,startParameter){
	DeviceJava.launchApplicationForResult(application,startParameter);
};
/**
 * Callback method to invoke when Device.launchApplicationForResult complete. <br>
 *  
 * @param {String} result The result passed to application.
 * 
 * @returns
 * @type void
 */
Device.prototype.onlaunchAppCallback = function(result){
};

//public String getDataDirectory()
Device.prototype.getDataDirectory = function(){
	return DeviceJava.getDataDirectory();
};

//public String getExternalStorageDirectory()
Device.prototype.getExternalStorageDirectory = function(){
	return DeviceJava.getExternalStorageDirectory();
};

//public boolean getExternalStorageState()
Device.prototype.getExternalStorageState = function(){
	return DeviceJava.getExternalStorageState();
};

//public void setMusicMute(boolean state)
Device.prototype.setMusicMute= function(state){
	DeviceJava.setMusicMute(state);
};

//int getPicHeight(String srcfile)
Device.prototype.getPicHeight = function(srcfile){
	return DeviceJava.getPicHeight(srcfile);
}

//int getPicWidth(String srcfile)
Device.prototype.getPicWidth = function(srcfile){
	return DeviceJava.getPicWidth(srcfile);
}

//public boolean compressPicture(String srcfile,String dstfile,int dstwidth,int dstheight)
Device.prototype.compressPicture =  function(srcfile,dstfile,quality,dstwidth,dstheight){
	return DeviceJava.compressPicture(srcfile,dstfile,quality,dstwidth,dstheight);
};

/**
 * Get array of file system root directories. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var v = Widget.Device.getFileSystemRoots();
 * 
 * @returns An array of String about file system roots.
 * @type Array
 */
Device.prototype.getFileSystemRoots = function() {
	return obj2Json(DeviceJava.getFileSystemRoots());
};

/**
 * Get the file system size in Byte unit. <br> 
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Device.getFileSystemSize("/sdcard");   
 * 
 * @param {String} fileSystemRoot The file system root.
 * 
 * @returns Returns the size of the file system in Byte, could either be a storage card or 
 * the memory of the device. This field should be a double value.
 * @type Number 
 */
Device.prototype.getFileSystemSize = function(fileSystemRoot) {
	return DeviceJava.getFileSystemSize(fileSystemRoot);
};

/**
 * This function is called to vibrate the device for a specific time period. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Device.vibrate(5);
 *
 * @param {Number} durationSeconds  The duration, in seconds, of a vibration event.
 * 
 * @returns
 * @type void
 */
Device.prototype.vibrate = function(durationSeconds) {
	DeviceJava.vibrate(durationSeconds);
};

/**
 * String on the clip board
 * 
 * @type String
 */
Device.prototype.getclipboardString = function(){
    return obj2Str(DeviceJava.getclipboardString());
};

Device.prototype.setclipboardString = function(clipboardString){
		DeviceJava.setclipboardString(clipboardString);
};

/**
 * the device power info.
 * 
 * @type PowerInfo
 */
Device.prototype.PowerInfo = new PowerInfo();

/**
 * the device account info.
 * 
 * @type AccountInfo
 */
Device.prototype.AccountInfo = new AccountInfo();

/**
 * the device info.
 * 
 * @type DeviceInfo
 */
Device.prototype.DeviceInfo = new DeviceInfo();

/**
 * the device State info.
 * 
 * @type DeviceStateInfo
 */
Device.prototype.DeviceStateInfo = new DeviceStateInfo();

/**
 * the device data network info.
 * 
 * @type DataNetworkInfo
 */
Device.prototype.DataNetworkInfo = new DataNetworkInfo();

/**
 * the device radio info.
 * 
 * @type RadioInfo
 */
Device.prototype.RadioInfo = new RadioInfo();

/**
 * the file of the file system on the device
 * 
 * @type File
 */
Device.prototype.File = new File();

/**
 * the device position info.
 * 
 * @type PositionInfo
 */
Device.prototype.PositionInfo = new PositionInfo();

/**
 * Name of the widget engine.
 * 
 * @type String
 */
Device.prototype.widgetEngineName = "JIL Widget Engine";

/**
 * The name of the party who provides the widget engine.
 * 
 * @type String
 */
Device.prototype.widgetEngineProvider = "JIL";

/**
 * The version of the widget engine.
 * 
 * @type String
 */
Device.prototype.widgetEngineVersion = "1.0";



//------------------------------------
//*********PIM.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is used for listing all functions and properties of 
 * JavaScript object: PIM.
 *
 * @version 0.1 
 */

PIM.prototype = new Object();

/**
 * @class PIM is the container object for JIL PIM objects. <br>
 * <br>PIM is a sub-object of <I>Widget</I>. So we use PIM in this way: Widget.PIM.XXX<br>
 */
function PIM() {
};
//Widget.PIM.prototype= new PIM();
/**
 * Add a contact to the device. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var contact = new Widget.PIM.AddresBookItem();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;contact.setAttributeValue("mobilePhone", "10086");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.PIM.addAddressBookItem(contact);<br>
 * 
 * @param {AddressBookItem} contact A Contact object to be added
 * 
 * @returns 
 * @type void
 */
PIM.prototype.addAddressBookItem = function(contact) {
	return PIMJava.addAddressBookItem(jsobj2Str(contact))
};

/**
 * Create a new contact group in the address book. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.PIM.createAddressBookGroup("Family");<br>
 * 
 * @param {String} groupName The name of the contact group to be created
 * 
 * @returns 
 * @type void
 */
PIM.prototype.createAddressBookGroup = function(groupName) {
	return PIMJava.createAddressBookGroup(groupName);
};

/**
 * Creates an empty AddressBookItem. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var contact = Widget.PIM.createAddressBookItem();<br>
 *
 * @returns an AddressBookItem object
 * @type AddressBookItem
 */
PIM.prototype.createAddressBookItem = function() {
	return new Widget.PIM.AddressBookItem();
};

/**
 * Delete a contact with the specified id. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.PIM.deleteAddressBookItem("1");<br> 
 * 
 * @param {String} contactid the unique identifier of the address book item to be deleted
 * 
 * @returns 
 * @type void
 */
PIM.prototype.deleteAddressBookItem = function(contactid) {
	return PIMJava.deleteAddressBookItem(contactid);
};

/**
 * Delete contact group in the address book. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.PIM.deleteAddressBookGroup("Family");<br> 
 * 
 * @param {String} groupName The name of the contact group to be deleted
 * 
 * @returns 
 * @type void
 */
PIM.prototype.deleteAddressBookGroup = function(groupName) {
	return PIMJava.deleteAddressBookGroup(groupName);
};

/**
 * Get a contact from the specified index. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var contact = Widget.PIM.getAddressBookItem("1");<br> 
 * 
 * @param {String} contactid the unique identifier of the address book item to be retrieved
 * 
 * @returns an AddressBookItem object
 * @type AddressBookItem
 */
PIM.prototype.getAddressBookItem = function(contactid) {
	var addressItem = new Widget.PIM.AddressBookItem();
	var  ItemArray = PIMJava.getAddressBookItem(contactid);
	alert(ItemArray);
	ItemArray = obj2Json(ItemArray);
	return ArrayToAddressBookItem(ItemArray);
};

/**
 * Return how many contacts are available on the phone. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var count = Widget.PIM.getAddressBookItemsCount(); <br>     
 * 
 * @returns a Number indicating how many contacts are present. This value should be an integer.
 * @type Number
 */
PIM.prototype.getAddressBookItemsCount = function() {
	return PIMJava.getAddressBookItemsCount();
};

/**
 * Return all unique listing of all group names associated with all contacts. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var groupNames = Widget.PIM.getAvailableAddressGroupNames(); <br>     
 * 
 * @returns a String array containing the name of all the groups .
 * @type Array
 */
PIM.prototype.getAvailableAddressGroupNames = function() {
	var groupNames = PIMJava.getAvailableAddressGroupNames();
	return obj2Json(groupNames);
	
};

/**
 * Find all contacts matching the supplied comparison contact. Wildcard are supported 
 * and searches are case-insensitive.<br>
 * E.g., assigning "*smith*" to the "full_name" attribute of the comparisonContact will 
 * return all contacts having 'smith' in the name.<br>
 * Widget can specify only return a range of matched files between startInx and endInx.<br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var contact = new Widget.PIM.AddresBookItem();<br> 
 * &nbsp;&nbsp;&nbsp;&nbsp;contact.setAttributeValue("mobilePhone", "10086");<br>    
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.PIM.findAddressBookItems(contact, 0, 9);<br> 
 * 
 * @param {AddressBookItem} comparisonContact A Contact containing attributes/values 
 * that are to be matched against the contact database
 * @param {Number} startInx an integer value to specify the index the returning search 
 * results starts from
 * @param {Number} endInx an integer value to specify the index to which the returning 
 * search results ends at 
 * 
 * @returns 
 * @type void
 */
PIM.prototype.findAddressBookItems = function(comparisonContact, startInx, endInx) {
	return	PIMJava.findAddressBookItems(jsobj2Str(comparisonContact),startInx,endInx);
};

/**
 * Callback method to invoke when PIM.findAddressBookItems completes. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.PIM.onAddressBookItemsFound = myCallBack;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallBack(addressBookItems) {<br>   
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;var size = addressBookItems.length;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br>
 * 
 * @param Array addressBookItemsFound An array of AddressBookItem objects
 * 
 * @returns
 * @type void
 */
PIM.prototype.onAddressBookItemsFound = function(addressBookItemsFound) {
};
    
PIM.prototype.onAddressBookItemsFoundProxy = function(addressBookItems){
	addressBookItems = obj2Str(addressBookItems);
	addressBookItems = addressBookItems.replace(/\\/g, "");   
	addressBookItems = addressBookItems.replace(/"{/g, "{");
	addressBookItems = addressBookItems.replace(/}"/g, "}");
	var array =  obj2Json(addressBookItems);
	for(i = 0; i<array.length-1; i++){
		array[i] = ArrayToAddressBookItem(array[i]);
	}
	Widget.PIM.onAddressBookItemsFound(array);
}
/** 
 * Add a calendar item to the device. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var day = new Date (2008, 9, 23, 0, 0, 0);<br>     
 * &nbsp;&nbsp;&nbsp;&nbsp;var calendarItem = new Widget.PIM.CalendarItem();<br>   
 * &nbsp;&nbsp;&nbsp;&nbsp;calendarItem.startTime = day;<br>   
 * &nbsp;&nbsp;&nbsp;&nbsp;calendarItem.eventName = "Meeting";<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;calendarItem.eventRecurrence = Widget.PIM.EventRecurrenceTypes.WEEKLY_ON_DAY;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.PIM.addCalendarItem(calendarItem);<br> 
 * 
 * @param {CalendarItem} calendarItem A ContactItem object to be added
 * 
 * @returns
 * @type void
 */
PIM.prototype.addCalendarItem = function(calendarItem) {	 
	return PIMJava.addCalendarItem(calendarItem.toJSONString());
};

/**
 * Delete a calendar item with a specified id. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.PIM.deleteCalendarItem("1");<br>      
 * 
 * @param {String} calendarId the unique identifier of the calendar item to be deleted.
 * 
 * @returns
 * @type void
 */
PIM.prototype.deleteCalendarItem = function(calendarId) {
	return PIMJava.deleteCalendarItem(calendarId);
};

/**
 * Get a calendar item of the specified index. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var calendarItem = Widget.PIM.getCalendarItem("1");<br>      
 * 
 * @param {String} calendarId the unique identifier of the calendar item to be deleted
 * 
 * @returns a CalendarItem object
 * @type CalendarItem
 */
PIM.prototype.getCalendarItem = function(calendarId) {
	var item = PIMJava.getCalendarItem(calendarId);
	item = obj2Json(item);
	return ArrayToCalendarItem(item);
};

/**
 * Find all calendar items matching the supplied itemToMatch. Wildcards are supported 
 * and searches are case-insensitive. <br> 
 * E.g., assigning "*meeting*" to the 'eventNotes' attribute of the itemToMatch will 
 * return all calendar items having 'meeting' in the event notes.<br>
 * Widget can specify only search a range of files between startInx and endInx. This 
 * is an asynchronous function, and will invoke PIM.onCalendarItemsFound() when search 
 * completes.<br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var calendarItem = new Widget.PIM.CalendarItem();<br>     
 * &nbsp;&nbsp;&nbsp;&nbsp;calendarItem.eventName = "*Meeting";<br>     
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.PIM.findCalendarItems(calendarItem, 0, 9); <br>     
 * 
 * @param {CalendarItem}itemToMatch A calendar item containing attributes/values that 
 * are to be matched against the calendar item database
 * @param {Number} startInx an integer value to specify the index the returning search 
 * results starts from
 * @param {Number} endInx an integer value to specify the index to which the returning 
 * search results ends at 
 * 
 * @returns
 * @type void
 */
PIM.prototype.findCalendarItems = function(itemToMatch, startInx, endInx) {
	return PIMJava.findCalendarItems(itemToMatch.toJSONString(), startInx, endInx);
};

/**
 * Callback method to invoke when PIM.findCalendarItems completes. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.PIM.onCalendarItemsFound = myCallBack;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallBack(calendarItems) {<br>   
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;var size = calendarItems.length;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *
 * @param Array calendarItemsFound An array of CalendarItem objects
 *
 * @returns
 * @type void
 */
PIM.prototype.onCalendarItemsFound = function(calendarItemsFound) {
};

PIM.prototype.onCalendarItemsFoundProxy = function(calendarItems){
	calendarItems = obj2Str(calendarItems);
	calendarItems = calendarItems.replace(/\\/g, "");   
	calendarItems = calendarItems.replace(/"{/g, "{");
	calendarItems = calendarItems.replace(/}"/g, "}");
	var array =  obj2Json(calendarItems);
	for(i = 0; i<array.length-1; i++){
		array[i] = ArrayToCalendarItem(array[i]);
	}
	Widget.PIM.onCalendarItemsFound(array);
}

/**
 * Find all events that are scheduled to happen between the specified start and end time. <br> 
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var startTime = new Date(2008, 9, 11, 0, 0, 0);<br>     
 * &nbsp;&nbsp;&nbsp;&nbsp;var endTime = new Date(2008, 9, 23, 0, 0, 0);<br>     
 * &nbsp;&nbsp;&nbsp;&nbsp;var addressBookItems = Widget.PIM.getCalendarItems(startTime, endTime);<br>     
 *
 * @param {Date} startTime the time that the event is scheduled to start
 * @param {Date} endTime the time that the event is scheduled to end
 *
 * @returns An array of CalendarItem objects.
 * @type Array
 */
PIM.prototype.getCalendarItems = function(startTime, endTime) {
 	var calendarItems = PIMJava.getCalendarItems(startTime, endTime);
	calendarItems = obj2Str(calendarItems);
	calendarItems = calendarItems.replace(/\\/g, "");   
	calendarItems = calendarItems.replace(/"{/g, "{");
	calendarItems = calendarItems.replace(/}"/g, "}");
	var array =  obj2Json(calendarItems);
	for(i = 0; i<array.length-1; i++){
		array[i] = ArrayToCalendarItem(array[i]);
	}
	return array;
};

/**
 * Call back method to invoke when the alarm associated with a calendar item starts. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.PIM.onCalendarItemAlert = myCallBack;<br>   
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallBack(calendarItem) {<br>   
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;var eventName = calendarItem.eventName; <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br>    
 *  
 * @param {CalendarItem} calendarItem CalendarItem object that the alarm is associated with
 * 
 * @returns
 * @type void  
 */
PIM.prototype.onCalendarItemAlert = function(calendarItem) {
};

/**
 * This is an asynchronous function that takes in an Array of AddressBookItems and 
 * exports them into a vCard file. <br> 
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var contact = Widget.PIM.getAddressBookItem("1");<br>   
 * &nbsp;&nbsp;&nbsp;&nbsp;var arrayContacts = new Array(contact);<br>     
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.PIM.exportAsVCard(arrayContacts); <br>     
 * 
 * @param Array addressBookItems the address book items that are to be exported 
 * 
 * @returns
 * @type void
 */
PIM.prototype.exportAsVCard = function(addressBookItems) {
};

/**
 * Callback method to invoke when the widget finishes exporting the vCard file. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.PIM.onVCardExportingFinish = myCallBack;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallBack(path) {<br>   
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br>
 *
 * @param {String} vCardFilePath a string value points to the location of the vCard file 
 *
 * @returns
 * @type void
 */
PIM.prototype.onVCardExportingFinish = function(vCardFilePath) {
};

/**
 * The AddressBookItem in PIM object.
 *
 * @type AddressBookItem
 */
//PIM.prototype.AddressBookItem = new Widget.PIM.AddressBookItem();
PIM.prototype.AddressBookItem = new AddressBookItem();
/**
 * The CalendarItem in PIM object.
 *
 * @type CalendarItem
 */
//PIM.prototype.CalendarItem = new Widget.PIM.CalendarItem();
PIM.prototype.CalendarItem = new CalendarItem();

/**
 * The EventRecurrenceTypes in PIM object.
 *
 * @type EventRecurrenceTypes
 */
PIM.prototype.EventRecurrenceTypes = new EventRecurrenceTypes();



//------------------------------------
//*********Telephony.js   ***********
//------------------------------------

/** 
 * @fileoverview This file is used for listing all functions and properties of 
 * JavaScript object: Telephony.
 *
 * @version 0.1 
 */
Telephony.prototype = new Object();

/**
 * @class Telephony is the container object for JIL Telephony objects. <br>
 * <br>
 * Telephony is a sub-object of <I>Widget</I>. So we use Telephony in this way: 
 * Widget.Telephony.XXX<br>
 */
function Telephony() {
};
//Widget.Telephony.prototype= new Telephony();
/**
 * Automatically initiates an outgoing voice call with the string specified. Expected to 
 * invoke the native dialer application.<br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Telephony.initiateVoiceCall("10086");
 * 
 * @param {String} phoneNumber a string containing the phone number to be dialed for the voice call.
 * 
 * @returns
 * @type void
 */
Telephony.prototype.initiateVoiceCall = function(phoneNumber) {
	return TelephonyJava.initiateVoiceCall(phoneNumber);
};

/**
 * Get the number of call records from the call log for the specified call record type. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Telephony.getCallRecordCnt(Widget.Telephony.CallRecordTypes.MISSED);
 * 
 * @param {String} callRecordType A String value corresponding to a CallRecordTypes entry
 * 
 * @returns the total number of records for the specified type. 0 is returned if the 
 * type is unknown
 * @type Number 
 */
Telephony.prototype.getCallRecordCnt = function(callRecordType) {
	alert("Telephony.prototype.getCallRecordCnt");
	return TelephonyJava.getCallRecordCnt(callRecordType);
};

/**
 * Retrieves the call record of the specified call record type by index. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Telephony.getCallRecord(Widget.Telephony.CallRecordTypes.MISSED, "10");
 *  
 * @param {String} callRecordType A String value corresponding to a CallRecordTypes entry
 * @param {String} id String value uniquely identifies the call record in the call log. 
 * This value is assigned by the widget engine. 
 * 
 * @returns  a CallRecord object specified by <i>id</i> parameter
 * @type CallRecord
 */
Telephony.prototype.getCallRecord = function(callRecordType, id) {
	var groupRecords = obj2Json(TelephonyJava.getCallRecord(callRecordType, id));
	
	if(groupRecords != null){
	    groupRecords.startTime = new Date(groupRecords.startTime);
	} 

	return groupRecords;
};

/**
 * Deletes the call record of the specified call record type by index. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Telephony.deleteCallRecord(Widget.Telephony.CallRecordTypes.MISSED, "123");
 * 
 * @param {String} callRecordType a String value corresponding to a CallRecordType entry
 * @param {String} id a String value uniquely identifies the call record in the call log. 
 * This value is assigned by the widget engine.
 * 
 * @returns
 * @type void
 */
Telephony.prototype.deleteCallRecord = function(callRecordType, id) {
	return TelephonyJava.deleteCallRecord(callRecordType, id);
};

/**
 * Deletes all call records of the specified call record type. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Telephony.deleteAllCallRecords(Widget.Telephony.CallRecordTypes.OUTGOING);
 * 
 * @param {String} callRecordType a String value corresponding to a CallRecordTypes entry 
 *  
 * @returns
 * @type void
 */
Telephony.prototype.deleteAllCallRecords = function(callRecordType) {
    return TelephonyJava.deleteAllCallRecords(callRecordType);
};
 
/**
 * Retrieves all call records of the specified call record type having a match against 
 * the specified fields defined in the comparisonRecord. Supports * as wildcards in 
 * Strings. Search is case insensitive.<br>
 * <br>
 * Widget can specify only return a range of matched files between startInx and endInx. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Telephony.onCallRecordsFound = myCallBack;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var record = new CallRecord();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;record.callRecordAddress = "55555555";<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Telephony.findCallRecords(record, 0, 9); 
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallBack(callRecords) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}
 * 
 * @param {CallRecord} comparisonRecord a CallRecord object containing fields to match 
 * against in the call records. Leave a field blank if you do not wish to check against 
 * it. If the CallRecordType field is blank, then all CallRecord types are searched. 
 * Wildcards are supported in the name strings and address strings. To search for records 
 * in a given time/date range, define the start time for the search in the startTime 
 * value of the comparisonRecord and set the durationSeconds to reflect the end time for 
 * the search.
 * @param {Number} startInx an integer value to specify the index the returning search 
 * results starts from
 * @param {Number} endInx an integer value to specify the index to which the returning 
 * search results ends at 
 * 
 * @returns 
 * @type void
 */ 
Telephony.prototype.findCallRecords = function(comparisonRecord, startInx, endInx) {
	return TelephonyJava.findCallRecords(comparisonRecord.callRecordType,comparisonRecord.callRecordId,
	    comparisonRecord.callRecordAddress,comparisonRecord.callRecordName,
		comparisonRecord.durationSeconds,comparisonRecord.startTime, startInx, endInx);
}; 

/**
 * This is a call back method which will be triggered when there's an received
 * call, missed call or outgoing call. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Telephony.onCallEvent = myCallBack;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallBack(callType, phoneNumber) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;if(callType == "missed") {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;var missedCall = phoneNumber;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}<br>
 * 
 * @param {String} callType identifies the type of the call event, possible values are 
 *                          "received", "missed" and "outgoing" 
 * @param {String} phoneNumber if this is an received or missed call, then this is 
 *                          the destination phone number; if this is an outgoing
 *                          call, then this is the source phone number. 
 * 
 * @returns 
 * @type void
 */
Telephony.prototype.setonCallEvent = function(callback){
	if(typeof callback !="function"){
		TelephonyJava.setonCallEvent(false);
	}else{
		this.onCallEvent=callback;
		TelephonyJava.setonCallEvent(true);
	}
};
Telephony.prototype.onCallEvent = function(callType, phoneNumber) {	
};
Telephony.prototype.onCallEventProxy = function(callsArray) {
	var array =  obj2Json(callsArray);
	Widget.Telephony.onCallEvent(array[0].callRecordType,array[0].phoneNumber);
}; 

/** 
 * This is a call back method to invoke when Telephony.findCallRecords completes.  <br>
 * <br> 
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Telephony.onCallRecordsFound = myCallBack;<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;var record = new CallRecord();<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;record.callRecordAddress = "55555555";<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.Telephony.findCallRecords(record, 0, 9); 
 * &nbsp;&nbsp;&nbsp;&nbsp;function myCallBack(callRecords) {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}
 * 
 * @param Array callRecords An array of CallRecord objects if either startInx or 
 * endInx is negative in Telephony.findCallRecords function, the returned callRecordsFound 
 * will be an empty array. if startInx equals endInx in Telephony.findCallRecords 
 * function, the returned callRecordsFound will contain only one item whose sequence 
 * number is startInx. if startInx is greater than endInx in Telephony.findCallRecords 
 * function, the returned callRecordsFound will be an empty array. if startInx is greater 
 * than the number of found items in Telephony.findCallRecords function, the returned 
 * callRecordsFound will be an empty array. if endInx is greater than the number of 
 * found items in Telephony.findCallRecords function, the returned callRecordsFound 
 * will contain items bwteeen startInx and the last returned item inclusively.
 * 
 * @returns 
 * @type void
 */
Telephony.prototype.onCallRecordsFound = function(callRecords) {
};

Telephony.prototype.onCallRecordsFoundProxy = function(callsArray){
	var array =  obj2Json(callsArray);
	if(array != null){
	    array[0].startTime = new Date(array[0].startTime);
	} ; 

	Widget.Telephony.onCallRecordsFound(array);
}
/**  
 *  The type of call record in the call log.
 * @type CallRecordTypes
 */
Telephony.prototype.CallRecordTypes = new CallRecordTypes();

/**
 * The record items in the call log.
 * @type CallRecord
 */
Telephony.prototype.CallRecord = new CallRecord();


//------------------------------------
//*********Widget.js   ***********
//------------------------------------


/** 
 * @fileoverview This file is used for listing all functions and properties of 
 * JavaScript object: Widget.
 *
 * @version 0.1 
 */

/**
 * @class Widget<br>
 */
function Widget() {
};

/**
 * Static Device object associated with this Widget
 * 
 * @type Device
 */
Widget.prototype.Device = new Device();

/**
 * Static PIM object associated with this Widget
 * 
 * @type PIM
 */
Widget.prototype.PIM = new PIM();

/**
 * Static Messaging object associated with this Widget
 * 
 * @type Messaging
 */
Widget.prototype.Messaging = new Messaging();

/**
 * Static Telephony object associated with this Widget
 * 
 * @type Telephony
 */
Widget.prototype.Telephony = new Telephony();

/**
 * Static Multimedia object associated with this Widget
 * 
 * @type Multimedia
 */
Widget.prototype.Multimedia = new Multimedia();

/**
 * This is a call back method which will be triggered when the widget is restored 
 * to default display mode. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function Widget.onRestore() {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;alert("widget is restored to default display mode");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}
 *
 * @returns 
 * @type void
*/
Widget.prototype.onRestore = function() {
};
	
/**
 * This is a call back method which will be triggered when the widget is displayed 
 * in the maximized display mode. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function Widget.onMaximize() {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;alert("widget is displayed in the maximized display mode.");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}
 *
 * @returns 
 * @type void
*/
Widget.prototype.onMaximize =function() {
};
	
/**
 * This is a call back method which will be triggered when the widget gains focus 
 * when multiple widgets are displayed on the same screen. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function Widget.onFocus() {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;alert("widget gains focus in floating mode.");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}
 *
 * @returns 
 * @type void
*/
Widget.prototype.onFocus = function() {
};
	
/**
 * A call back method is called when the widget is triggered to wake up. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function Widget.onWakeup() {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;alert("widget wakes up");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}
 *
 * @returns 
 * @type void
*/
Widget.prototype.onWakeup = function() {
};

/**
 * A call back method is called when key pressed down. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;function Widget.onWakeup() {<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;alert("widget wakes up");<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;}
 *
 * @param {Integer} keycode the code of key pressed down
 * 
 * @returns 
 * @type void
*/
Widget.prototype.onKeydown = function(keycode) {
	if(keycode == 4)
		Widget.exit();
};

/**
 * This method takes a String argument, <i>Key</i>. When called, this method will 
 * return a string that has previously been stored with the setPrefernceForKey 
 * method, or undefined if the key does not exist.
 * 
 * @param {String} key identifier of the preference
 * 
 * @returns preference value of the key
 * @type String
 */
Widget.prototype.preferenceForKey = function(key) {
	return obj2Str(WidgetJava.preferenceForKey(key));
};

/**
 * This method takes two String arguments, <i>preference</i> and <i>key</i>. When 
 * called, this method will store the string in the <i>preference</i> argument with 
 * the key named in the <i>key</i> argument for later retrieval using the 
 * preferenceForKey() method. If the setPreferenceForKey() method is called with 
 * the value null in the preference argument, the key identified in the <i>key</i> 
 * argument will be deleted.
 * 
 * @param {String} key identifier of the preference
 * @param {String} preference string value of the preference
 * 
 * @returns
 * @type void
 */
Widget.prototype.setPreferenceForKey = function(key, preference) {
	WidgetJava.setPreferenceForKey(key, preference);
};


/**
 * This method will launch the device browser implementation to the url specified 
 * in the string argument. <br>
 * <br>
 * e.g.<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Widget.openURL("http://www.jil.org");
 * 
 * @param {String} URL to access, upon browser launch
 * 
 * @returns 
 * @type void
*/
Widget.prototype.openURL = function(url) {
	WidgetJava.openURL(url);
};

Widget.prototype.requestFocus = function(){
  WidgetJava.requestFocus();
};

Widget.prototype.showOptionsMenu = function(){
	WidgetJava.showOptionsMenu();
};

Widget.prototype.exit = function(){
	WidgetJava.exit();
};

//	public boolean getAccEnterKey()
Widget.prototype.getKeyListener = function(){
	return WidgetJava.getKeyListener();
};

//	public void setAccEnterKey
Widget.prototype.setKeyListener = function(isAccept){
	WidgetJava.setKeyListener(isAccept);
};

Widget.prototype.setFocusable = function(isFocusable){
	WidgetJava.setFocusable(isFocusable);
};

window.Widget = new Widget();
Widget.PIM.AddressBookItem=AddressBookItem;
Widget.PIM.CalendarItem= CalendarItem;
Widget.Messaging.Attachment= Attachment;
Widget.Telephony.CallRecord=CallRecord;
Widget.Device.File=File;
Widget.Device.prototype=new Device();
Widget.Messaging.prototype=new Messaging();
Widget.PIM.prototype=new PIM();
Widget.Telephony.prototype=new Telephony();

