package cheque.handover.services.Utility;

import org.springframework.stereotype.Component;

@Component
public class GetExcelDataForReportUser {

    public String query(String applicationNo) {
        String query = "\n" +
                "select 'Combined Loan Agreement'as\"Document Name\",'122346'as\"IRN\",\n" +
                "'1702208952696'as\"Field(0).id\",'Authorised Signatory Name on behalf of Shubham Finance'as\"Field(0).name\",'Shubham Housing Development Finance Compnay Limited'as\"Field(0).value\",\n" +
                "'1702209131361'as\"Field(1).id\",'Borrower Name'as\"Field(1).name\",appl.\"Customer Name\"as\"Field(1).value\",\n" +
                "'1702209192965'as\"Field(2).id\",'Borrower Address'as\"Field(2).name\",appl.\"Borrower Address\"as\"Field(2).value\",\n" +
                "'1702211436019'as\"Field(3).id\",'Title for Borrower Name(Mr.)'as\"Field(3).name\",CASE WHEN appl.\"Salutation\"='Mr' THEN 'True' else ''END as\"Field(3).value\",\n" +
                "'1702211446710'as\"Field(4).id\",'Title for Borrower Name(Ms.)'as\"Field(4).name\",CASE WHEN appl.\"Salutation\"='Mrs' THEN 'True' else ''END as\"Field(4).value\",\n" +
                "'1702209439389'as\"Field(5).id\",'Authorised Signatory Name on behalf of Borrower' as\"Field(5).name\",appl.\"Customer Name\"as\"Field(5).value\",\n" +
                "'1702209818056'as\"Field(6).id\",'Title for Co-Borrower 1 Name(Mr.)'as\"Field(6).name\",CASE WHEN appl.\"Salutation1\"='Mr' THEN 'True' else ''END as\"Field(6).value\",\n" +
                "'1702209836524'as\"Field(7).id\",'Title for Co-Borrower 1 Name(Ms.)'as\"Field(7).name\",CASE WHEN appl.\"Salutation1\"='Mrs' THEN 'True' else ''END as\"Field(7).value\",\n" +
                "'1702213589429'as\"Field(8).id\",'Co-Borrower 1 Name' as\"Field(8).name\",appl.\"CoApplicant1 Name\"as\"Field(8).value\",\n" +
                "'1702209854836'as\"Field(9).id\",'Title for Co-Borrower 2 Name(Mr.)'as\"Field(9).name\",CASE WHEN appl.\"Salutation2\"='Mr' THEN 'True' else ''END as\"Field(9).value\",\n" +
                "'1702209866025'as\"Field(10).id\",'Title for Co-Borrower 2 Name(Ms.)'as\"Field(10).name\",CASE WHEN appl.\"Salutation2\"='Mrs' THEN 'True' else ''END as\"Field(10).value\",\n" +
                "'1702213589432'as\"Field(11).id\",'Co-Borrower 2 Name' as\"Field(11).name\",appl.\"CoApplicant2 Name\"as\"Field(11).value\",\n" +
                "'1702209883198'as\"Field(12).id\",'Title for Co-Borrower 3 Name(Mr.)'as\"Field(12).name\",CASE WHEN appl.\"Salutation3\"='Mr' THEN 'True' else ''END as\"Field(12).value\",\n" +
                "'1702209894382'as\"Field(13).id\",'Title for Co-Borrower 3 Name(Ms.)'as\"Field(13).name\",CASE WHEN appl.\"Salutation3\"='Mrs' THEN 'True' else ''END as\"Field(13).value\",\n" +
                "'1702213589435'as\"Field(14).id\",'Co-Borrower 3 Name' as\"Field(14).name\",appl.\"CoApplicant3 Name\"as\"Field(14).value\",\n" +
                "'1702209758344'as\"Field(15).id\",'Title for Witness Name(Mr.)'as\"Field(15).name\",''as\"Field(15).value\",\n" +
                "'1702209769648'as\"Field(16).id\",'Title for Witness Name(Ms.)'as\"Field(16).name\",''as\"Field(16).value\",\n" +
                "'1702213589438'as\"Field(17).id\",'Witness Name as'\"Field(17).name\",appl.witness_name as\"Field(17).value\",\n" +
                "'1702210652317'as\"Field(18).id\",'Borrower Name (DPN)'as\"Field(18).name\",appl.\"Customer Name\"as\"Field(18).value\",\n" +
                "'1702210719791'as\"Field(19).id\",'Loan Amount in Figures (DPN)'as\"Field(19).name\",appl.\"Sanction Loan Amount\"as\"Field(19).value\",\n" +
                "'1702213589441'as\"Field(20).id\",'Loan Amount in Words (DPN)'as\"Field(20).name\",appl.\"SA words\"as\"Field(20).value\",\n" +
                "'1702210804113'as\"Field(21).id\",'Interest Rate (DPN)' as\"Field(21).name\",appl.\"Sanctioned ROI\"as\"Field(21).value\",\n" +
                "'1702210586587'as\"Field(22).id\",'Place (DPN)'as\"Field(22).name\",'Gurgaon'as\"Field(22).value\",\n" +
                "'1702213589444'as\"Field(23).id\",'Date (DPN)'as\"Field(23).name\",TO_CHAR(appl.\"Sanction Date\",'DD-MM-YYYY')as\"Field(23).value\",\n" +
                "'1702211032374'as\"Field(24).id\",'Demand Promissory Note Dated'as\"Field(24).name\",TO_CHAR(appl.\"Sanction Date\",'DD-MM-YYYY')as\"Field(24).value\",\n" +
                "'1702213589446'as\"Field(25).id\",'Loan Amount in Figures (Letter of Continuity)'as\"Field(25).name\",appl.\"Sanction Loan Amount\"as\"Field(25).value\",\n" +
                "'1702213589447'as\"Field(26).id\",'Loan Amount in Words (Letter of Continuity)'as\"Field(26).name\",appl.\"SA words\"as\"Field(26).value\",\n" +
                "'1702213589448'as\"Field(27).id\",'Place (Letter of Continuity)'as\"Field(27).name\",'Gurgaon'as\"Field(27).value\",\n" +
                "'1702213589449'as\"Field(28).id\",'Date (Letter of Continuity)'as\"Field(28).name\",appl.\"day\" as\"Field(28).value\",\n" +
                "'1702213589450'as\"Field(29).id\",'Day (Letter of Continuity)'as\"Field(29).name\",appl.\"month\" as\"Field(29).value\",\n" +
                "'1702213589451'as\"Field(30).id\",'Year (Letter of Continuity)'as\"Field(30).name\",appl.\"year\"as\"Field(30).value\",\n" +
                "'1702211934599'as\"Field(31).id\",'Place (Power of Attorney)'as\"Field(31).name\",'Gurgaon'as\"Field(31).value\",\n" +
                "'1702213589453'as\"Field(32).id\",'Date (Power of Attorney)'as\"Field(32).name\",appl.\"day\"as\"Field(32).value\",\n" +
                "'1702213589454'as\"Field(33).id\",'Day (Power of Attorney)'as\"Field(33).name\",appl.\"month\" as\"Field(33).value\",\n" +
                "'1702213589455'as\"Field(34).id\",'Year (Power of Attorney)'as\"Field(34).name\",appl.\"year\"as\"Field(34).value\",\n" +
                "'1702213589456'as\"Field(35).id\",'Borrower Name (Power of Attorney)'as\"Field(35).name\",appl.\"Customer Name\"as\"Field(35).value\",\n" +
                "'1702213589457'as\"Field(36).id\",'Borrower Address (Power of Attorney)'as\"Field(36).name\",appl.\"Borrower Address\"as\"Field(36).value\",\n" +
                "'1702213589458'as\"Field(37).id\",'Title for Co-Borrower 1 Name (Power of Attorney)(Mr.)'as\"Field(37).name\",CASE WHEN appl.\"Salutation1\"='Mr' THEN 'True' else ''END as\"Field(37).value\",\n" +
                "'1702213589459'as\"Field(38).id\",'Title for Co-Borrower 1 Name (Power of Attorney)(Ms.)'as\"Field(38).name\",CASE WHEN appl.\"Salutation1\"='Mrs' THEN 'True' else ''END as\"Field(38).value\",\n" +
                "'1702213589460'as\"Field(39).id\",'Co-Borrower 1 Name  (Power of Attorney)'as\"Field(39).name\",appl.\"CoApplicant1 Name\"as\"Field(39).value\",\n" +
                "'1702213589461'as\"Field(40).id\",'Title for Co-Borrower 2 Name (Power of Attorney)(Mr.)'as\"Field(40).name\",CASE WHEN appl.\"Salutation2\"='Mr' THEN 'True' else ''END as\"Field(40).value\",\n" +
                "'1702213589462'as\"Field(41).id\",'Title for Co-Borrower 2 Name (Power of Attorney)(Ms.)'as\"Field(41).name\",CASE WHEN appl.\"Salutation2\"='Mrs' THEN 'True' else ''END as\"Field(41).value\",\n" +
                "'1702213589463'as\"Field(42).id\",'Co-Borrower 2 Name (Power of Attorney)'as\"Field(42).name\",appl.\"CoApplicant2 Name\"as\"Field(42).value\",\n" +
                "'1702213589464'as\"Field(43).id\",'Title for Co-Borrower 3 Name (Power of Attorney)(Mr.)'as\"Field(43).name\",CASE WHEN appl.\"Salutation3\"='Mr' THEN 'True' else ''END as\"Field(43).value\",\n" +
                "'1702213589465'as\"Field(44).id\",'Title for Co-Borrower 3 Name (Power of Attorney)(Ms.)'as\"Field(44).name\",CASE WHEN appl.\"Salutation3\"='Mrs' THEN 'True' else ''END as\"Field(44).value\",\n" +
                "'1702213589466'as\"Field(45).id\",'Co-Borrower 3 Name (Power of Attorney)'as\"Field(45).name\",appl.\"CoApplicant3 Name\"as\"Field(45).value\",\n" +
                "'1702213589467'as\"Field(46).id\",'Title for Witness Name (Power of Attorney)(Mr.)'as\"Field(46).name\",''as\"Field(46).value\",\n" +
                "'1702213589469'as\"Field(47).id\",'Title for Witness Name (Power of Attorney)(Ms.)'as\"Field(47).name\",''as\"Field(47).value\",\n" +
                "'1702213589470'as\"Field(48).id\",'Witness Name  (Power of Attorney)'as\"Field(48).name\",appl.witness_name as\"Field(48).value\",\n" +
                "'1702212879864'as\"Field(49).id\",'Property Description (Schedule)'as\"Field(49).name\",''as\"Field(49).value\",\n" +
                "'1702214913800'as\"Field(50).id\",'Borrower Name (Memorandum of Entry)'as\"Field(50).name\",appl.\"Customer Name\"as\"Field(50).value\",\n" +
                "'1717094311160'as\"Field(51).id\",'Acting through Proprietor/director/Partner'as\"Field(51).name\",''as\"Field(51).value\",\n" +
                "'1712832519855'as\"Field(52).id\",'Loan Agreement/ Deposit Date (Memorandum of Entry)'as\"Field(52).name\",TO_CHAR(appl.\"Sanction Date\",'DD-MM-YYYY')as\"Field(52).value\",\n" +
                "'1712832519856'as\"Field(53).id\",'Place (Memorandum of Entry)'as\"Field(53).name\",'Gurgaon' as\"Field(53).value\",\n" +
                "'1712832519857'as\"Field(54).id\",'Loan Amt (Memorandum of Entry)'as\"Field(54).name\",appl.\"Sanction Loan Amount\"as\"Field(54).value\",\n" +
                "'1712831279043'as\"Field(55).id\",'Loan Account/Application No. (Memorandum of Entry)'as\"Field(55).name\",appl.\"Application Number\"as\"Field(55).value\",\n" +
                "'1712832519859'as\"Field(56).id\",'Shubham office where deposited (Memorandum of Entry)'as\"Field(56).name\",'Gurgaon'as\"Field(56).value\",\n" +
                "'1712832519860'as\"Field(57).id\",'Shubham representative (Memorandum of Entry)'as\"Field(57).name\",appl.\"Sourcing RM Name\"as\"Field(57).value\",\n" +
                "'1712831919185'as\"Field(58).id\",'Property Bearing Number (Memorandum of Entry)'as\"Field(58).name\",''as\"Field(58).value\",\n" +
                "'1712831930752'as\"Field(59).id\",'Property Area (Memorandum of Entry)'as\"Field(59).name\",appl.\"Build up Area\"as\"Field(59).value\",\n" +
                "'1712832519863'as\"Field(60).id\",'Property Location (Memorandum of Entry)'as\"Field(60).name\",appl.\"Property Address\"as\"Field(60).value\",\n" +
                "'1702219366151'as\"Field(61).id\",'Title for Borrower (Acknowledgement)(Shri)'as\"Field(61).name\",CASE WHEN appl.\"Salutation\"='Mr' THEN 'True' else ''END as\"Field(61).value\",\n" +
                "'1702219375976'as\"Field(62).id\",'Title for Borrower (Acknowledgement)(Smt.)'as\"Field(62).name\",CASE WHEN appl.\"Salutation\"='Mrs' THEN 'True' else ''END as\"Field(62).value\",\n" +
                "'1702219382724'as\"Field(63).id\",'Title for Borrower (Acknowledgement)(Kum.)'as\"Field(63).name\",''as\"Field(63).value\",\n" +
                "'1702219335653'as\"Field(64).id\",'Borrower Name (Acknowledgement)'as\"Field(64).name\",appl.\"Customer Name\" as\"Field(64).value\",\n" +
                "'INDIVIDUAL_LOAN'as\"RegistrationType\",appl.\"Application Number\"as \"LoanNo\",''as\"SanctionNo\",\n" +
                "appl.\"Property State\"as\"State\",appl.\"Branch Name\"as\"Branch\",appl.\"Branch Address\"as\"BranchAddress\",\n" +
                "TO_CHAR(appl.\"Sanction Date\",'YYYY-MM-DD')as\"LoanSanctionDate\",appl.\"Installment Amount\"as\"InstallmentAmt\",\n" +
                "appl.\"Sanctioned ROI\"as\"InterestRate\",appl.\"Sanction Loan Amount\" as\"SanctionAmount\",\n" +
                "appl.\"Sanction Tenure\"as\"Tenure\",'Financial'as\"TypeOfDebt\",'No'as\"AccountClosedFlag\",\n" +
                "'Funded'as\"FundedType\",'INR'as\"LoanCurrency\",'CREDIT_FACILITY'as\"CreditSub-Type\",\n" +
                "appl.\"Type of Loan\"as\"FacilityName\",'0'as\"AmtOverdue\",'0'as\"OtherCharges\",\n" +
                "TO_CHAR(appl.\"Sanction Date\",'YYYY-MM-DD')as\"DebtStartDate\",'0'as\"InterestAmount\", 'NA'as\"OldDebtRefNo\",\n" +
                "'0'as\"PrincipalOutstanding\",'NA'as\"LoanRemarks\",'0'as\"TotalOutstanding\",'NA'as\"CreditorBusinessUnit\",\n" +
                "'0'as\"DrawingPower\",'0'as\"DaysPastDue\",'NA'as\"DocRefNo\",'NA'as\"Event\",'NA'as\"ExpiryDate\",\n" +
                "'INR'as\"CurrencyOfDebt\",'NA'as\"ClaimExpiryDate\",'NA'as\"ContractRefNo\",'NA'as\"VendorCode\",'NA'as\"PortalID\",\n" +
                "'Shubham Housing Development Finance LTD'as\"Stamp(0).FirstParty\",appl.\"Customer Name\"as\"Stamp(0).SecondParty\",\n" +
                "''as\"Stamp(0).FirstPartyPin\",''as\"Stamp(0).SecondPartyPin\",''as\"Stamp(0).FirstPartyIDType\",\n" +
                "''as\"Stamp(0).SecondPartyIDType\",'NA'as\"Stamp(0).FirstPartyIDNumber\",'NA'as\"Stamp(0).SecondPartyIDNumber\",\n" +
                "'100'as\"Stamp(0).StampAmount\",appl.\"Sanction Loan Amount\"as\"Stamp(0).Consideration\",\n" +
                "'Loan Agreement'as\"Stamp(0).DocDescription\",'Shubham Housing Development Finance LTD'as\"Stamp(0).StampDutyPayer\",\n" +
                "Decode(appl.\"Property State\",'Maharashtra','21','Uttar Pradesh','1','Haryana','NA','Telangana',\n" +
                "'6','Delhi','1003','Andhra Pradesh','9','Punjab','1005','Uttarakhand','3','Gujarat','108',\n" +
                "'Tamil Nadu','1076','Madhya Pradesh','4','Rajasthan','1151',0) as\"Stamp(0).Article\",'Shubham Housing Development Finance LTD'as\"Stamp(1).FirstParty\",\n" +
                "appl.\"Customer Name\"as\"Stamp(1).SecondParty\",\n" +
                "''as\"Stamp(1).FirstPartyPin\",''as\"Stamp(1).SecondPartyPin\",''as\"Stamp(1).FirstPartyIDType\",\n" +
                "''as\"Stamp(1).SecondPartyIDType\",'NA'as\"Stamp(1).FirstPartyIDNumber\",'NA'as\"Stamp(1).SecondPartyIDNumber\",\n" +
                "'100'as\"Stamp(1).StampAmount\",appl.\"Sanction Loan Amount\"as\"Stamp(1).Consideration\",\n" +
                "'Loan Agreement'as\"Stamp(1).DocDescription\",'Shubham Housing Development Finance LTD'as\"Stamp(1).StampDutyPayer\",\n" +
                "Decode(appl.\"Property State\",'Maharashtra','21','Uttar Pradesh','1','Haryana','NA','Telangana',\n" +
                "'6','Delhi','1003','Andhra Pradesh','9','Punjab','1005','Uttarakhand','3','Gujarat','108',\n" +
                "'Tamil Nadu','1076','Madhya Pradesh','4','Rajasthan','1151',0)as\"Stamp(1).Article\",'Shubham Housing Development Finance LTD'as\"Stamp(2).FirstParty\",\n" +
                "appl.\"Customer Name\"as\"Stamp(2).SecondParty\",\n" +
                "''as\"Stamp(2).FirstPartyPin\",''as\"Stamp(2).SecondPartyPin\",''as\"Stamp(2).FirstPartyIDType\",\n" +
                "''as\"Stamp(2).SecondPartyIDType\",'NA'as\"Stamp(2).FirstPartyIDNumber\",'NA'as\"Stamp(2).SecondPartyIDNumber\",\n" +
                "'100'as\"Stamp(2).StampAmount\",appl.\"Sanction Loan Amount\"as\"Stamp(2).Consideration\",\n" +
                "'Loan Agreement'as\"Stamp(2).DocDescription\",'Shubham Housing Development Finance LTD'as\"Stamp(2).StampDutyPayer\",\n" +
                "Decode(appl.\"Property State\",'Maharashtra','21','Uttar Pradesh','1','Haryana','NA','Telangana',\n" +
                "'6','Delhi','1003','Andhra Pradesh','9','Punjab','1005','Uttarakhand','3','Gujarat','108',\n" +
                "'Tamil Nadu','1076','Madhya Pradesh','4','Rajasthan','1151',0)as\"Stamp(2).Article\",\n" +
                "appl.\"Customer Name\"as\"Invitee(0).name\",''as\"Invitee(0).email\",appl.\"Mobile Number\"as\"Invitee(0).phone\",\n" +
                "TO_CHAR(appl.\"DOB\",'YYYY')as\"Invitee(0).aadhaar.verifyYob\",\n" +
                "SUBSTR(appl.\"aadhar\",9,4)as\"Invitee(0).aadhaar.verifyTitle\",appl.\"Gender\" as\"Invitee(0).aadhaar.verifyGender\",\n" +
                "appl.\"Customer Name\"as\"Invitee(0).Partyname\",''as\"Invitee(0).Primaryemail\",appl.\"Mobile Number\"as\"Invitee(0).Primarymobile\",\n" +
                "appl.\"Customer Name\"as\"Invitee(0).ContactName\",\n" +
                "'Debtor'as\"Invitee(0).RelationshipOfPartyWithLoan\",TO_CHAR(appl.\"DOB\",'YYYY-MM-DD')as\"Invitee(0).DoB/Incorporation\",\n" +
                "'RESIDENT_INDIVIDUAL'as\"Invitee(0).LegalConstitution\",\n" +
                "''as\"Invitee(0).AlternateEmailofParty\",''as\"Invitee(0).AlternateMobileOfParty\",\n" +
                "'PAN_CARD'as\"Invitee(0).OfficialDocType\",appl.\"Identification Number\"as\"Invitee(0).OfficialDocId\",\n" +
                "appl.\"Borrower Address\"as\"Invitee(0).RegisteredAddressOfTheParty\",\n" +
                "appl.\"Pincode\"as\"Invitee(0).Permanent/RegisteredAddressPIN\",\n" +
                "'NA'as\"Invitee(0).ContactDesignation\",'NA'as\"Invitee(0).PartyCommunicationAddress\",\n" +
                "''as\"Invitee(0).PartyCommunicationAddressPIN\",''as\"Invitee(0).CorpIdentificationNo\",\n" +
                "''as\"Invitee(0).CKYCKIN\",'INDIAN_ENTITY'as\"Invitee(0).PartyType\",''as\"Invitee(0).Is_Individual\",\n" +
                "''as\"Invitee(0).SignatoryGender\",''as\"Invitee(0).BusinessUnit\",\n" +
                "appl.\"CoApplicant1 Name\"as\"Invitee(1).name\",''as\"Invitee(1).email\",appl.\"Mobile No1\"as\"Invitee(1).phone\",\n" +
                "appl.\"DOB1\"as\"Invitee(1).aadhaar.verifyYob\",appl.\"aadhar1\"as\"Invitee(1).aadhaar.verifyTitle\",\n" +
                "appl.\"Gender1\"as\"Invitee(1).aadhaar.verifyGender\",\n" +
                "appl.\"CoApplicant2 Name\"as\"Invitee(2).name\",''as\"Invitee(2).email\",appl.\"Mobile No2\"as\"Invitee(2).phone\",\n" +
                "appl.\"DOB2\"as\"Invitee(2).aadhaar.verifyYob\",appl.\"aadhar2\"as\"Invitee(2).aadhaar.verifyTitle\",\n" +
                "appl.\"Gender2\"as\"Invitee(2).aadhaar.verifyGender\",\n" +
                "appl.\"CoApplicant3 Name\"as\"Invitee(3).name\",''as\"Invitee(3).email\",appl.\"Mobile No3\"as\"Invitee(3).phone\",\n" +
                "appl.\"DOB3\"as\"Invitee(3).aadhaar.verifyYob\",appl.\"aadhar3\"as\"Invitee(3).aadhaar.verifyTitle\",\n" +
                "appl.\"Gender3\"as\"Invitee(3).aadhaar.verifyGender\",\n" +
                "'Bijayendra Kumar Jha'as\"Invitee(4).name\",'bijayendra.jha@shubham.co'as\"Invitee(4).email\",'7048951725'as\"Invitee(4).phone\"\n" +
                "from \n" +
                "(SELECT a.\"Application Number\",CASE WHEN b.\"Gender\"='Male' THEN 'Mr' WHEN b.\"Gender\"='Female' THEN 'Mrs' ELSE ''END as \"Salutation\",\n" +
                "b.\"Customer Name\",c.\"Address 1\"||' '||c.\"Address 2\"||' '||c.\"Address 3\"||' '||c.\"Landmark\"||' '||c.\"City\"||' '||c.\"State\"||' '||c.\"Pincode\" as \"Borrower Address\",\n" +
                "c.\"Pincode\"as\"Pincode\",trunc(\"Sanction Date\") as \"Sanction Date\",\n" +
                "Decode(b.\"Gender\", 'Male','M','Female','F','')as\"Gender\",\n" +
                "a.\"Sanction Loan Amount\",a.\"Product\",a.\"Sanction Tenure\",a.\"Sanctioned ROI\",\n" +
                "a.\"Installment Amount\",a.\"Product Type\"as\"Type of Loan\",a.\"Branch Name\",\n" +
                "a.\"Loan Purpose Description\",a.\"Loan Purpose\",g.witness_name,EXTRACT(Day FROM a.\"Sanction Date\") \"day\",\n" +
                "EXTRACT(Month FROM a.\"Sanction Date\") \"month\",EXTRACT(Year FROM a.\"Sanction Date\") \"year\",\n" +
                "regexp_replace(a.\"Sourcing RM Name\", '^[^ ]+ (.*)$', '\\1')as\"Sourcing RM Name\",''as \"Applicant Father/Husband Name\",\n" +
                "h.\"Branch State\",\"APPLICANT'S_FATHER_NAME\"as\"Father Name\",\n" +
                "\"Branch Flat/Plot Number\"||' '||\"Branch Address Line 2\"||' '||\"Branch Address Line 3\"||' '||\n" +
                "\"Branch City\"||' '||\"Branch District\"||' '||\"Branch Pincode\" as\"Branch Address\",\n" +
                "c.\"Mobile Number\",trunc(b.\"Date Of Birth\")as\"DOB\",\n" +
                "b.\"Identification Number\",i.\"Build up Area\",i.\"Property Address 1\"||' '||i.\"Property Address 2\"\n" +
                "||' '||i.\"Property Address 3\"||' '||i.\"Property City\"||' '||i.\"Property State\"\n" +
                "||' '||i.\"Property Pincode\" as\"Property Address\",i.\"Property State\",\"sanction\".\"SA words\",\n" +
                "(select \"Identification Number\" from NEO_CAS_LMS_SIT1_SH.IDENTIFICATION_DETAILS_OLD\n" +
                "where \"Identification Type\"='Aadhar No.' and \"Customer Number\"=a.\"Customer Number\")\"aadhar\",\n" +
                "coap.\"Salutation1\",coap.\"CoApplicant1 Name\",coap.\"Mobile No1\",coap.\"aadhar1\",coap.\"Gender1\",coap.\"DOB1\",\n" +
                "coap.\"Salutation2\",coap.\"CoApplicant2 Name\",coap.\"Mobile No2\",coap.\"aadhar2\",coap.\"Gender2\",coap.\"DOB2\",\n" +
                "coap.\"Salutation3\",coap.\"CoApplicant3 Name\",coap.\"Mobile No3\",coap.\"aadhar3\",coap.\"Gender3\",coap.\"DOB3\"\n" +
                "from neo_Cas_lms_sit1_sh.Application_newprod a left join\n" +
                "neo_cas_lms_sit1_sh.\"Individual Customer\" b on(a.\"Customer Number\"=b.\"Customer Number\") left join \n" +
                "neo_cas_lms_sit1_sh.\"Address Details\" c on(a.\"Customer Number\"=c.\"Customer Number\")left join\n" +
                "NEO_CAS_LMS_SIT1_SH.MISC_GOFTR_FIELD g on(g.APPLICATION_NUMBER=a.\"Application Number\")left join\n" +
                "neo_cas_lms_sit1_sh.\"Branch\" h on(h.\"Branch Code\"=a.\"Branch Code\") left join\n" +
                "neo_cas_lms_sit1_Sh.\"Property Details\" i on(a.\"Application Number\"=i.\"Application Number\")\n" +
                " --=============Father Name============\n" +
                "left join\n" +
                "(select * from NEO_CAS_LMS_SIT1_SH.Family_Details where \"S_No\" like '%1%')j \n" +
                "on(a.\"Application Number\"=j.\"Application_Number\")\n" +
                " --=============Sanction Amount in Words============\n" +
                "left join\n" +
                " (WITH words (\"Application Number\", original, \"Sanction Loan Amount\", suffix, result, lvl, mxlvl ) AS (\n" +
                "SELECT \"Application Number\",\"Sanction Loan Amount\",TRUNC( \"Sanction Loan Amount\" / 10000000 ),CAST( 'CRORE ' AS VARCHAR2(4000)),\n" +
                "CAST(RTRIM(CASE WHEN MOD( TRUNC( \"Sanction Loan Amount\"/ 100000 ), 100 ) > 0 THEN TO_CHAR(\n" +
                "TO_DATE(MOD( TRUNC( \"Sanction Loan Amount\"/ 100000 ), 100 ),'J'),'JSP') || ' LAKH ' END||\n" +
                "CASE WHEN \"Sanction Loan Amount\"= 0 THEN 'ZERO' WHEN MOD( \"Sanction Loan Amount\", 100000 ) > 0 THEN TO_CHAR(\n" +
                "TO_DATE(MOD(\"Sanction Loan Amount\", 100000),'J'),'JSP') || ' 'END)AS VARCHAR2(4000)), 1,CEIL(LENGTH(ABS(\"Sanction Loan Amount\"))/7)\n" +
                "FROM neo_cas_lms_sit1_sh.Application_Newprod where \"Application Number\" ='APPL05370117'\n" +
                "UNION ALL\n" +
                "SELECT \"Application Number\",original,TRUNC( \"Sanction Loan Amount\" / 10000000 ),'CRORE ' || suffix,\n" +
                "RTRIM(CASE WHEN MOD( TRUNC( \"Sanction Loan Amount\" / 100000 ), 100 ) > 0 \n" +
                "THEN TO_CHAR(TO_DATE(MOD( TRUNC( \"Sanction Loan Amount\" / 100000 ), 100 ),'J'),'JSP') || ' LAKH ' || suffix END\n" +
                "|| CASE WHEN MOD( \"Sanction Loan Amount\", 100000 ) > 0 THEN TO_CHAR(TO_DATE(MOD(\"Sanction Loan Amount\", 100000 ),\n" +
                "'J'),'JSP') || ' ' || suffix END || result ), lvl + 1, mxlvl \n" +
                "FROM words WHERE lvl < mxlvl\n" +
                ") SELECT \"Application Number\",original as\"Sanction Loan Amount\", result as\"SA words\"\n" +
                "from words WHERE lvl = mxlvl) \"sanction\"\n" +
                "on(a.\"Application Number\"=\"sanction\".\"Application Number\") \n" +
                "--=====Adding COBORROWERS=========\n" +
                "left join\n" +
                "(select \"CoApplicant1 Application No\",\"Salutation1\",\"CoApplicant1 Name\",--\"Email ID1\",\n" +
                "\"Mobile No1\",SUBSTR(\"aadhar1\",9,4)as\"aadhar1\",\n" +
                "Decode(\"Gender1\", 'Male','M','Female','F','')as\"Gender1\",TO_CHAR(\"DOB1\",'YYYY')\"DOB1\",\n" +
                "\"Salutation2\",\"CoApplicant2 Name\",\"Mobile No2\",SUBSTR(\"aadhar2\",9,4)as\"aadhar2\",\n" +
                "Decode(\"Gender2\", 'Male','M','Female','F','')as\"Gender2\",TO_CHAR(\"DOB2\",'YYYY')\"DOB2\",\n" +
                "\"Salutation3\",\"CoApplicant3 Name\",\"Mobile No3\",SUBSTR(\"aadhar3\",9,4)as\"aadhar3\",\n" +
                "Decode(\"Gender3\", 'Male','M','Female','F','')as\"Gender3\",TO_CHAR(\"DOB3\",'YYYY')\"DOB3\"\n" +
                "from ( (select t1.* from (WITH test AS (select a.\"Application Number\"as\"CoApplicant1 Application No\",\n" +
                "CASE WHEN \"Gender\"='Male' THEN 'Mr' WHEN \"Gender\"='Female' THEN 'Mrs' ELSE ''END as \"Salutation1\",\n" +
                "b.\"Customer Name\" as\"CoApplicant1 Name\",c.\"Mobile Number\"as\"Mobile No1\",\"Gender\" as\"Gender1\",\n" +
                "trunc(b.\"Date Of Birth\")as\"DOB1\",\n" +
                "(select \"Identification Number\" from NEO_CAS_LMS_SIT1_SH.IDENTIFICATION_DETAILS_OLD\n" +
                "where \"Identification Type\"='Aadhar No.' and \"Customer Number\"=b.\"Customer Number\")\"aadhar1\",\n" +
                "RANK() OVER(PARTITION BY a.\"Application Number\" order BY b.\"Customer Number\" DESC ) rank1\n" +
                "from neo_cas_lms_sit1_sh.\"APPLICATION_NEWPROD\" a left join\n" +
                "neo_cas_lms_sit1_sh.\"Individual Customer\" b on((b.\"Applicant Type\"='Gurantor' or \n" +
                "b.\"Applicant Type\"='Co-Applicant') and a.\"Application Number\"=b.\"Application Number\")left join \n" +
                "neo_cas_lms_sit1_sh.\"Address Details\" c on(c.\"Customer Number\"=b.\"Customer Number\" and \n" +
                "\"Addresstype\"='Residential Address')\n" +
                "where a.\"Application Number\" ='APPL05373362')\n" +
                " SELECT * from test where rank1=1) t1 ) tt1  left join ( select t2.* from (WITH test AS (\n" +
                " select a.\"Application Number\"as\"CoApplicant2 Application No\",\n" +
                " CASE WHEN \"Gender\"='Male' THEN 'Mr' WHEN \"Gender\"='Female' THEN 'Mrs' ELSE ''END as \"Salutation2\",\n" +
                "b.\"Customer Name\" as\"CoApplicant2 Name\",c.\"Mobile Number\"as\"Mobile No2\",\n" +
                "\"Gender\" as\"Gender2\",trunc(b.\"Date Of Birth\")as\"DOB2\",\n" +
                "(select \"Identification Number\" from NEO_CAS_LMS_SIT1_SH.IDENTIFICATION_DETAILS_OLD\n" +
                "where \"Identification Type\"='Aadhar No.' and \"Customer Number\"=b.\"Customer Number\")\"aadhar2\",\n" +
                "RANK() OVER(PARTITION BY a.\"Application Number\" order BY b.\"Customer Number\" DESC ) rank2\n" +
                "from neo_cas_lms_sit1_sh.\"APPLICATION_NEWPROD\" a left join\n" +
                "neo_cas_lms_sit1_sh.\"Individual Customer\" b on((b.\"Applicant Type\"='Gurantor' or \n" +
                "b.\"Applicant Type\"='Co-Applicant') and a.\"Application Number\"=b.\"Application Number\")left join \n" +
                "neo_cas_lms_sit1_sh.\"Address Details\" c on(c.\"Customer Number\"=b.\"Customer Number\" and \n" +
                "\"Addresstype\"='Residential Address')\n" +
                "where a.\"Application Number\" ='APPL05370117')\n" +
                " SELECT * from test where rank2=2) t2 ) tt2 \n" +
                " on (tt1.\"CoApplicant1 Application No\"=tt2.\"CoApplicant2 Application No\")\n" +
                " left join ( select t3.* from (WITH test AS (\n" +
                " select a.\"Application Number\"as\"CoApplicant3 Application No\",\n" +
                " CASE WHEN \"Gender\"='Male' THEN 'Mr' WHEN \"Gender\"='Female' THEN 'Mrs' ELSE ''END as \"Salutation3\",\n" +
                "b.\"Customer Name\" as\"CoApplicant3 Name\",c.\"Mobile Number\"as\"Mobile No3\",\"Gender\" as\"Gender3\",\n" +
                "trunc(b.\"Date Of Birth\")as\"DOB3\",\n" +
                "(select \"Identification Number\" from NEO_CAS_LMS_SIT1_SH.IDENTIFICATION_DETAILS_OLD\n" +
                "where \"Identification Type\"='Aadhar No.' and \"Customer Number\"=b.\"Customer Number\")\"aadhar3\",\n" +
                "RANK() OVER(PARTITION BY a.\"Application Number\" order BY b.\"Customer Number\" DESC ) rank3\n" +
                "from neo_cas_lms_sit1_sh.\"APPLICATION_NEWPROD\" a left join\n" +
                "neo_cas_lms_sit1_sh.\"Individual Customer\" b on((b.\"Applicant Type\"='Gurantor' or \n" +
                "b.\"Applicant Type\"='Co-Applicant') and a.\"Application Number\"=b.\"Application Number\")left join \n" +
                "neo_cas_lms_sit1_sh.\"Address Details\" c on(c.\"Customer Number\"=b.\"Customer Number\" and \n" +
                "\"Addresstype\"='Residential Address')\n" +
                "where a.\"Application Number\" ='APPL05373362')\n" +
                " SELECT * from test where rank3=3) t3) tt3 on (tt1.\"CoApplicant1 Application No\"=tt3.\"CoApplicant3 Application No\")\n" +
                " ))coap\n" +
                "on(a.\"Application Number\"=coap.\"CoApplicant1 Application No\")\n" +
                " where a.\"Application Number\"='APPL05373362'and c.\"Addresstype\"='Residential Address')appl;";
        return query;
    }
}
