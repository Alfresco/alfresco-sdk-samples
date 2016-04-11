var greeting = new XML(config.script);
model.greetingActive = greeting.active[0].toString();
model.greetingText = greeting.text[0].toString();
model.personName = person.properties.firstName + ' ' + person.properties.lastName + "!";