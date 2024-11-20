package cheque.handover.services.Utility;

import org.springframework.stereotype.Component;

@Component
public class GetExcelDataForReportUser {

    public String query(String applicationNo) {
        String query = "select 'Combined Loan Agreement'as\"Document Name\",appl.\"Application Number\"as\"IRN\",\n" +
                "'1702208952696'as\"Field0id\",'Authorised Signatory Name on behalf of Shubham Finance'as\"Field0name\",'Shubham Housing Development Finance Compnay Limited'as\"Field0value\",\n" +
                "'1702209131361'as\"Field1id\",'Borrower Name'as\"Field1name\",appl.\"Customer Name\"as\"Field1value\",\n" +
                "'1702209192965'as\"Field2id\",'Borrower Address'as\"Field2name\",appl.\"Borrower Address\"as\"Field2value\",\n" +
                "'1702211436019'as\"Field3id\",'Title for Borrower Name(Mr.)'as\"Field3name\",CASE WHEN appl.\"Salutation\"='Mr' THEN 'True' else ''END as\"Field3value\",\n" +
                "'1702211446710'as\"Field4id\",'Title for Borrower Name(Ms.)'as\"Field4name\",CASE WHEN appl.\"Salutation\"='Mrs' THEN 'True' else ''END as\"Field4value\",\n" +
                "'1702209439389'as\"Field5id\",'Authorised Signatory Name on behalf of Borrower' as\"Field5name\",appl.\"Customer Name\"as\"Field5value\",\n" +
                "'1702209818056'as\"Field6id\",'Title for Co-Borrower 1 Name(Mr.)'as\"Field6name\",CASE WHEN appl.\"Salutation1\"='Mr' THEN 'True' else ''END as\"Field6value\",\n" +
                "'1702209836524'as\"Field7id\",'Title for Co-Borrower 1 Name(Ms.)'as\"Field7name\",CASE WHEN appl.\"Salutation1\"='Mrs' THEN 'True' else ''END as\"Field7value\",\n" +
                "'1702213589429'as\"Field8id\",'Co-Borrower 1 Name' as\"Field8name\",appl.\"CoApplicant1 Name\"as\"Field8value\",\n" +
                "'1702209854836'as\"Field9id\",'Title for Co-Borrower 2 Name(Mr.)'as\"Field9name\",CASE WHEN appl.\"Salutation2\"='Mr' THEN 'True' else ''END as\"Field9value\",\n" +
                "'1702209866025'as\"Field10id\",'Title for Co-Borrower 2 Name(Ms.)'as\"Field10name\",CASE WHEN appl.\"Salutation2\"='Mrs' THEN 'True' else ''END as\"Field10value\",\n" +
                "'1702213589432'as\"Field11id\",'Co-Borrower 2 Name' as\"Field11name\",appl.\"CoApplicant2 Name\"as\"Field11value\",\n" +
                "'1702209883198'as\"Field12id\",'Title for Co-Borrower 3 Name(Mr.)'as\"Field12name\",CASE WHEN appl.\"Salutation3\"='Mr' THEN 'True' else ''END as\"Field12value\",\n" +
                "'1702209894382'as\"Field13id\",'Title for Co-Borrower 3 Name(Ms.)'as\"Field13name\",CASE WHEN appl.\"Salutation3\"='Mrs' THEN 'True' else ''END as\"Field13value\",\n" +
                "'1702213589435'as\"Field14id\",'Co-Borrower 3 Name' as\"Field14name\",appl.\"CoApplicant3 Name\"as\"Field14value\",\n" +
                "'1702209758344'as\"Field15id\",'Title for Witness Name(Mr.)'as\"Field15name\",''as\"Field15value\",\n" +
                "'1702209769648'as\"Field16id\",'Title for Witness Name(Ms.)'as\"Field16name\",''as\"Field16value\",\n" +
                "'1702213589438'as\"Field17id\",'Witness Name as'\"Field17name\",appl.witness_name as\"Field17value\",\n" +
                "'1702210652317'as\"Field18id\",'Borrower Name (DPN)'as\"Field18name\",appl.\"Customer Name\"as\"Field18value\",\n" +
                "'1702210719791'as\"Field19id\",'Loan Amount in Figures (DPN)'as\"Field19name\",appl.\"Sanction Loan Amount\"as\"Field19value\",\n" +
                "'1702213589441'as\"Field20id\",'Loan Amount in Words (DPN)'as\"Field20name\",appl.\"SA words\"as\"Field20value\",\n" +
                "'1702210804113'as\"Field21id\",'Interest Rate (DPN)' as\"Field21name\",appl.\"Sanctioned ROI\"as\"Field21value\",\n" +
                "'1702210586587'as\"Field22id\",'Place (DPN)'as\"Field22name\",appl.\"Branch Name\"as\"Field22value\",\n" +
                "'1702213589444'as\"Field23id\",'Date (DPN)'as\"Field23name\",TO_CHAR(sysdate,'DD-MM-YYYY')as\"Field23value\",\n" +
                "'1702211032374'as\"Field24id\",'Demand Promissory Note Dated'as\"Field24name\",TO_CHAR(appl.\"Sanction Date\",'DD-MM-YYYY')as\"Field24value\",\n" +
                "'1702213589446'as\"Field25id\",'Loan Amount in Figures (Letter of Continuity)'as\"Field25name\",appl.\"Sanction Loan Amount\"as\"Field25value\",\n" +
                "'1702213589447'as\"Field26id\",'Loan Amount in Words (Letter of Continuity)'as\"Field26name\",appl.\"SA words\"as\"Field26value\",\n" +
                "'1702213589448'as\"Field27id\",'Place (Letter of Continuity)'as\"Field27name\",appl.\"Branch Name\"as\"Field27value\",\n" +
                "'1702213589449'as\"Field28id\",'Date (Letter of Continuity)'as\"Field28name\",appl.\"day\" as\"Field28value\",\n" +
                "'1702213589450'as\"Field29id\",'Day (Letter of Continuity)'as\"Field29name\",appl.\"month\" as\"Field29value\",\n" +
                "'1702213589451'as\"Field30id\",'Year (Letter of Continuity)'as\"Field30name\",appl.\"year\"as\"Field30value\",\n" +
                "'1702211934599'as\"Field31id\",'Place (Power of Attorney)'as\"Field31name\",appl.\"Branch Name\"as\"Field31value\",\n" +
                "'1702213589453'as\"Field32id\",'Date (Power of Attorney)'as\"Field32name\",appl.\"day\"as\"Field32value\",\n" +
                "'1702213589454'as\"Field33id\",'Day (Power of Attorney)'as\"Field33name\",appl.\"month\" as\"Field33value\",\n" +
                "'1702213589455'as\"Field34id\",'Year (Power of Attorney)'as\"Field34name\",appl.\"year\"as\"Field34value\",\n" +
                "'1702213589456'as\"Field35id\",'Borrower Name (Power of Attorney)'as\"Field35name\",appl.\"Customer Name\"as\"Field35value\",\n" +
                "'1702213589457'as\"Field36id\",'Borrower Address (Power of Attorney)'as\"Field36name\",appl.\"Borrower Address\"as\"Field36value\",\n" +
                "'1702213589458'as\"Field37id\",'Title for Co-Borrower 1 Name (Power of Attorney)(Mr.)'as\"Field37name\",CASE WHEN appl.\"Salutation1\"='Mr' THEN 'True' else ''END as\"Field37value\",\n" +
                "'1702213589459'as\"Field38id\",'Title for Co-Borrower 1 Name (Power of Attorney)(Ms.)'as\"Field38name\",CASE WHEN appl.\"Salutation1\"='Mrs' THEN 'True' else ''END as\"Field38value\",\n" +
                "'1702213589460'as\"Field39id\",'Co-Borrower 1 Name  (Power of Attorney)'as\"Field39name\",appl.\"CoApplicant1 Name\"as\"Field39value\",\n" +
                "'1702213589461'as\"Field40id\",'Title for Co-Borrower 2 Name (Power of Attorney)(Mr.)'as\"Field40name\",CASE WHEN appl.\"Salutation2\"='Mr' THEN 'True' else ''END as\"Field40value\",\n" +
                "'1702213589462'as\"Field41id\",'Title for Co-Borrower 2 Name (Power of Attorney)(Ms.)'as\"Field41name\",CASE WHEN appl.\"Salutation2\"='Mrs' THEN 'True' else ''END as\"Field41value\",\n" +
                "'1702213589463'as\"Field42id\",'Co-Borrower 2 Name (Power of Attorney)'as\"Field42name\",appl.\"CoApplicant2 Name\"as\"Field42value\",\n" +
                "'1702213589464'as\"Field43id\",'Title for Co-Borrower 3 Name (Power of Attorney)(Mr.)'as\"Field43name\",CASE WHEN appl.\"Salutation3\"='Mr' THEN 'True' else ''END as\"Field43value\",\n" +
                "'1702213589465'as\"Field44id\",'Title for Co-Borrower 3 Name (Power of Attorney)(Ms.)'as\"Field44name\",CASE WHEN appl.\"Salutation3\"='Mrs' THEN 'True' else ''END as\"Field44value\",\n" +
                "'1702213589466'as\"Field45id\",'Co-Borrower 3 Name (Power of Attorney)'as\"Field45name\",appl.\"CoApplicant3 Name\"as\"Field45value\",\n" +
                "'1702213589467'as\"Field46id\",'Title for Witness Name (Power of Attorney)(Mr.)'as\"Field46name\",''as\"Field46value\",\n" +
                "'1702213589469'as\"Field47id\",'Title for Witness Name (Power of Attorney)(Ms.)'as\"Field47name\",''as\"Field47value\",\n" +
                "'1702213589470'as\"Field48id\",'Witness Name  (Power of Attorney)'as\"Field48name\",appl.witness_name as\"Field48value\",\n" +
                "'1702212879864'as\"Field49id\",'Property Description (Schedule)'as\"Field49name\",''as\"Field49value\",\n" +
                "'1702214913800'as\"Field50id\",'Borrower Name (Memorandum of Entry)'as\"Field50name\",appl.\"Customer Name\"as\"Field50value\",\n" +
                "'1717094311160'as\"Field51id\",'Acting through Proprietor/director/Partner'as\"Field51name\",''as\"Field51value\",\n" +
                "'1712832519855'as\"Field52id\",'Loan Agreement/ Deposit Date (Memorandum of Entry)'as\"Field52name\",TO_CHAR(sysdate,'DD-MM-YYYY')as\"Field52value\",\n" +
                "'1712832519856'as\"Field53id\",'Place (Memorandum of Entry)'as\"Field53name\",appl.\"Branch Name\" as\"Field53value\",\n" +
                "'1712832519857'as\"Field54id\",'Loan Amt (Memorandum of Entry)'as\"Field54name\",appl.\"Sanction Loan Amount\"as\"Field54value\",\n" +
                "'1712831279043'as\"Field55id\",'Loan Account/Application No. (Memorandum of Entry)'as\"Field55name\",appl.\"Application Number\"as\"Field55value\",\n" +
                "'1712832519859'as\"Field56id\",'Shubham office where deposited (Memorandum of Entry)'as\"Field56name\",appl.\"Branch Name\"as\"Field56value\",\n" +
                "'1712832519860'as\"Field57id\",'Shubham representative (Memorandum of Entry)'as\"Field57name\",appl.\"Sourcing RM Name\"as\"Field57value\",\n" +
                "'1712831919185'as\"Field58id\",'Property Bearing Number (Memorandum of Entry)'as\"Field58name\",''as\"Field58value\",\n" +
                "'1712831930752'as\"Field59id\",'Property Area (Memorandum of Entry)'as\"Field59name\",appl.\"Build up Area\"as\"Field59value\",\n" +
                "'1712832519863'as\"Field60id\",'Property Location (Memorandum of Entry)'as\"Field60name\",appl.\"Property Address\"as\"Field60value\",\n" +
                "'1702219366151'as\"Field61id\",'Title for Borrower (Acknowledgement)(Shri)'as\"Field61name\",'True'as\"Field61value\",\n" +
                "'1702219375976'as\"Field62id\",'Title for Borrower (Acknowledgement)(Smt.)'as\"Field62name\",'' as\"Field62value\",\n" +
                "'1702219382724'as\"Field63id\",'Title for Borrower (Acknowledgement)(Kum.)'as\"Field63name\",''as\"Field63value\",\n" +
                "'1702219335653'as\"Field64id\",'Borrower Name (Acknowledgement)'as\"Field64name\",appl.\"Sourcing RM Name\" as\"Field64value\",\n" +
                "'INDIVIDUAL_LOAN'as\"RegistrationType\",appl.\"Application Number\"as \"LoanNo\",''as\"SanctionNo\",\n" +
                "appl.\"Property State\"as\"State\",\n" +
                "\n" +
                "appl.\"Branch Name\"as\"Branch\",\n" +
                "\n" +
                "appl.\"Branch Address\"as\"BranchAddress\",\n" +    /////////////////////
                "TO_CHAR(appl.\"Sanction Date\",'YYYY-MM-DD')as\"LoanSanctionDate\",appl.\"Installment Amount\"as\"InstallmentAmt\",\n" +
                "appl.\"Sanctioned ROI\"as\"InterestRate\",appl.\"Sanction Loan Amount\" as\"SanctionAmount\",\n" +
                "appl.\"Sanction Tenure\"as\"Tenure\",'Financial'as\"TypeOfDebt\",'No'as\"AccountClosedFlag\",\n" +
                "'Funded'as\"FundedType\",'INR'as\"LoanCurrency\",'CREDIT_FACILITY'as\"CreditSubType\",\n" +
                "appl.\"Type of Loan\"as\"FacilityName\",'0'as\"AmtOverdue\",'0'as\"OtherCharges\",\n" +
                "TO_CHAR(appl.\"Sanction Date\",'YYYY-MM-DD')as\"DebtStartDate\",'0'as\"InterestAmount\", 'NA'as\"OldDebtRefNo\",\n" +
                "'0'as\"PrincipalOutstanding\",'NA'as\"LoanRemarks\",'0'as\"TotalOutstanding\",'NA'as\"CreditorBusinessUnit\",\n" +
                "'0'as\"DrawingPower\",'0'as\"DaysPastDue\",'NA'as\"DocRefNo\",'NA'as\"Event\",'NA'as\"ExpiryDate\",\n" +
                "'INR'as\"CurrencyOfDebt\",'NA'as\"ClaimExpiryDate\",'NA'as\"ContractRefNo\",'NA'as\"VendorCode\",'NA'as\"PortalID\",\n" +
                "'Shubham Housing Development Finance LTD'as\"Stamp0FirstParty\",appl.\"Customer Name\"as\"Stamp0SecondParty\",\n" +
                "''as\"Stamp0FirstPartyPin\",''as\"Stamp0SecondPartyPin\",''as\"Stamp0FirstPartyIDType\",\n" +
                "''as\"Stamp0SecondPartyIDType\",'NA'as\"Stamp0FirstPartyIDNumber\",'NA'as\"Stamp0SecondPartyIDNumber\",\n" +
                "'100'as\"Stamp0Amount\",appl.\"Sanction Loan Amount\"as\"Stamp0Consideration\",\n" +
                "'Loan Agreement'as\"Stamp0DocDescription\",'Shubham Housing Development Finance LTD'as\"Stamp0StampDutyPayer\",\n" +
                "Decode(appl.\"Property State\",'Maharashtra','21','Uttar Pradesh','0001','Haryana','NA','Telangana',\n" +
                "'6','Delhi','1003','Andhra Pradesh','0009','Punjab','1005','Uttarakhand','0003','Gujarat','0108',\n" +
                "'Tamil Nadu','1076','Madhya Pradesh','4','Rajasthan','1151',0) as\"Stamp0Article\",'Shubham Housing Development Finance LTD'as\"stamp1FirstParty\",\n" +
                "appl.\"Customer Name\"as\"stamp1SecondParty\",\n" +
                "''as\"stamp1FirstPartyPin\",''as\"stamp1SecondPartyPin\",''as\"stamp1FirstPartyIDType\",\n" +
                "''as\"stamp1SecondPartyIDType\",'NA'as\"stamp1FirstPartyIDNumber\",'NA'as\"stamp1SecondPartyIDNumber\",\n" +
                "'100'as\"Stamp1Amount\",appl.\"Sanction Loan Amount\"as\"stamp1Consideration\",\n" +
                "'Loan Agreement'as\"stamp1DocDescription\",'Shubham Housing Development Finance LTD'as\"stamp1StampDutyPayer\",\n" +
                "Decode(appl.\"Property State\",'Maharashtra','21','Uttar Pradesh','0001','Haryana','NA','Telangana',\n" +
                "'6','Delhi','1003','Andhra Pradesh','0009','Punjab','1005','Uttarakhand','0003','Gujarat','0108',\n" +
                "'Tamil Nadu','1076','Madhya Pradesh','4','Rajasthan','1151',0)as\"stamp1Article\",'Shubham Housing Development Finance LTD'as\"stamp2FirstParty\",\n" +
                "appl.\"Customer Name\"as\"stamp2SecondParty\",\n" +
                "''as\"stamp2FirstPartyPin\",''as\"stamp2SecondPartyPin\",''as\"stamp2FirstPartyIDType\",\n" +
                "''as\"stamp2SecondPartyIDType\",'NA'as\"stamp2FirstPartyIDNumber\",'NA'as\"stamp2SecondPartyIDNumber\",\n" +
                "'100'as\"Stamp2Amount\",appl.\"Sanction Loan Amount\"as\"stamp2Consideration\",\n" +
                "'Loan Agreement'as\"stamp2DocDescription\",'Shubham Housing Development Finance LTD'as\"stamp2StampDutyPayer\",\n" +
                "Decode(appl.\"Property State\",'Maharashtra','21','Uttar Pradesh','0001','Haryana','NA','Telangana',\n" +
                "'6','Delhi','1003','Andhra Pradesh','0009','Punjab','1005','Uttarakhand','0003','Gujarat','0108',\n" +
                "'Tamil Nadu','1076','Madhya Pradesh','4','Rajasthan','1151',0)as\"stamp2Article\",\n" +
                "appl.\"Customer Name\"as\"Invitee0name\",''as\"Invitee0email\",appl.\"Mobile Number\"as\"Invitee0phone\",\n" +
                "TO_CHAR(appl.\"DOB\",'YYYY')as\"Invitee0aadhaar_verifyYob\",\n" +
                "SUBSTR(appl.\"aadhar\",9,4)as\"Invitee0aadhaar_verifyTitle\",appl.\"Gender\" as\"Invitee0aadhaar_verifyGender\",\n" +
                "appl.\"Customer Name\"as\"Invitee0Partyname\",''as\"Invitee0Primaryemail\",appl.\"Mobile Number\"as\"Invitee0Primarymobile\",\n" +
                "appl.\"Customer Name\"as\"Invitee0ContactName\",\n" +
                "'Debtor'as\"Invitee0RelationshipOfPartyWithLoan\",TO_CHAR(appl.\"DOB\",'YYYY-MM-DD')as\"Invitee0DoBIncorporation\",\n" +
                "'RESIDENT_INDIVIDUAL'as\"Invitee0LegalConstitution\",\n" +
                "''as\"Invitee0AlternateEmailofParty\",''as\"Invitee0AlternateMobileOfParty\",\n" +
                "'PAN_CARD'as\"Invitee0OfficialDocType\",appl.\"Identification Number\"as\"Invitee0OfficialDocId\",\n" +
                "REGEXP_REPLACE(regexp_replace(appl.\"Borrower Address\", '[^a-zA-Z|,0-9]','F '),'\\s{2,}', ' ')as\"Invitee0RegisteredAddressOfTheParty\",\n" +  ///////////
                "appl.\"Pincode\"as\"Invitee0PermanentRegisteredAddressPIN\",\n" +
                "'NA'as\"Invitee0ContactDesignation\",'NA'as\"Invitee0PartyCommunicationAddress\",\n" +
                "''as\"Invitee0PartyCommunicationAddressPIN\",''as\"Invitee0CorpIdentificationNo\",\n" +
                "''as\"Invitee0CKYCKIN\",'INDIAN_ENTITY'as\"Invitee0PartyType\",''as\"Invitee0Is_Individual\",\n" +
                "''as\"Invitee0SignatoryGender\",''as\"Invitee0BusinessUnit\",\n" +
                "appl.\"CoApplicant1 Name\"as\"Invitee1name\",''as\"Invitee1email\",appl.\"Mobile No1\"as\"Invitee1phone\",\n" +
                "appl.\"DOB1\"as\"Invitee1aadhaar_verifyYob\",appl.\"aadhar1\"as\"Invitee1aadhaar_verifyTitle\",\n" +
                "appl.\"Gender1\"as\"Invitee1aadhaar_verifyGender\",\n" +
                "appl.\"CoApplicant2 Name\"as\"Invitee2name\",''as\"Invitee2email\",appl.\"Mobile No2\"as\"Invitee2phone\",\n" +
                "appl.\"DOB2\"as\"Invitee2aadhaar_verifyYob\",appl.\"aadhar2\"as\"Invitee2aadhaar_verifyTitle\",\n" +
                "appl.\"Gender2\"as\"Invitee2aadhaar_verifyGender\",\n" +
                "appl.\"CoApplicant3 Name\"as\"Invitee3name\",''as\"Invitee3email\",appl.\"Mobile No3\"as\"Invitee3phone\",\n" +
                "appl.\"DOB3\"as\"Invitee3aadhaar_verifyYob\",appl.\"aadhar3\"as\"Invitee3aadhaar_verifyTitle\",\n" +
                "appl.\"Gender3\"as\"Invitee3aadhaar_verifyGender\",\n" +
                "'Bijayendra Kumar Jha'as\"Invitee4name\",'bijayendra.jha@shubham.co'as\"Invitee4email\",'7048951725'as\"Invitee4phone\"\n" +
                "from \n" +
                "(SELECT a.\"Application Number\",CASE WHEN b.\"Gender\"='Male' THEN 'Mr' WHEN b.\"Gender\"='Female' THEN 'Mrs' ELSE ''END as \"Salutation\",\n" +
                "b.\"Customer Name\",c.\"Address 1\"||' '||c.\"Address 2\"||' '||c.\"Address 3\"||' '||c.\"Landmark\"||' '||c.\"City\"||' '||c.\"State\"||' '||c.\"Pincode\" as \"Borrower Address\",\n" +
                "c.\"Pincode\"as\"Pincode\",trunc(\"Sanction Date\") as \"Sanction Date\",\n" +
                "Decode(b.\"Gender\", 'Male','M','Female','F','')as\"Gender\",\n" +
                "a.\"Sanction Loan Amount\",a.\"Product\",a.\"Sanction Tenure\",a.\"Sanctioned ROI\",\n" +
                "a.\"Installment Amount\",a.\"Product Type\"as\"Type of Loan\",a.\"Branch Name\",\n" +
                "a.\"Loan Purpose Description\",a.\"Loan Purpose\",g.witness_name,EXTRACT(Day FROM sysdate) \"day\",\n" +
                "EXTRACT(Month FROM sysdate) \"month\",EXTRACT(Year FROM sysdate) \"year\",\n" +
                "regexp_replace(a.\"Sourcing RM Name\", '^[^ ]+ (.*)$', '\\1')as\"Sourcing RM Name\",''as \"Applicant Father/Husband Name\",\n" +
                "h.\"Branch State\",\"APPLICANT'S_FATHER_NAME\"as\"Father Name\",\n" +
                "h.\"Branch Name\"||', '||\"Branch State\"||', '||\"Branch Pincode\" as\"Branch Address\",\n" + ////////////
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
                "FROM neo_cas_lms_sit1_sh.Application_Newprod where \"Application Number\" ='APPL05378458'\n" +
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
                "where a.\"Application Number\" IN ("+applicationNo+"))\n" +
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
                "where a.\"Application Number\" IN ("+applicationNo+"))\n" +
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
                "where a.\"Application Number\" IN ("+applicationNo+"))\n" +
                " SELECT * from test where rank3=3) t3) tt3 on (tt1.\"CoApplicant1 Application No\"=tt3.\"CoApplicant3 Application No\")\n" +
                " ))coap\n" +
                "on(a.\"Application Number\"=coap.\"CoApplicant1 Application No\")\n" +
                    " where a.\"Application Number\" IN ("+applicationNo+")and c.\"Addresstype\"='Residential Address')appl";
        return query;
    }
}
