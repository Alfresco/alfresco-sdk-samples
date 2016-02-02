var filterValue = args.filter;
if (filterValue == null) {
    filterValue = "";
}
var connector = remote.connect("alfresco");
var peopleJSONString = connector.get("/api/people?filter=" + filterValue);

// Don't use this: var peopleJSON = eval('(' + peopleJSONString + ')');, eval is not secure
var peopleJSON = jsonUtils.toObject(peopleJSONString);

model.people = peopleJSON["people"];
model.filterValue = filterValue;


/* A call like:

 http://localhost:8080/alfresco/service/api/people?filter=m

 will give a response JSON such as:

 {
     people: [
         {
         url: "/alfresco/service/api/people/mjackson",
         userName: "mjackson",
         enabled: false,
         avatar: "api/node/workspace/SpacesStore/519ebedc-0827-4fba-a8e3-c51e39385e0c/content/thumbnails/avatar",
         firstName: "Mike",
         lastName: "Jackson",
         jobtitle: "Web Site Manager",
         organization: "Green Energy",
         organizationId: null,
         location: "Threepwood, UK",
         telephone: "012211331100",
         mobile: "012211331100",
         email: "mjackson@example.com",
         companyaddress1: "100 Cavendish Street",
         companyaddress2: "Threepwood",
         companyaddress3: "UK",
         companypostcode: "ALF1 SAM1",
         companytelephone: "",
         companyfax: "",
         companyemail: "",
         skype: "mjackson",
         instantmsg: "",
         userStatus: "Working on a new web design for the corporate site",
         userStatusTime: {
            iso8601: "2011-02-15T20:13:09.649Z"
         },
         googleusername: "",
         quota: -1,
         sizeCurrent: 8834773,
         emailFeedDisabled: false,
         persondescription: "Mike is a demo user for the sample Alfresco Team site."
         }
    ]
 }


 */