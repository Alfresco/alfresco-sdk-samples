var renderingEngineName = 'reformat';
var renditionDefinitionName = 'cm:htmlRenditionDef';
var renditionDef = renditionService.createRenditionDefinition(renditionDefinitionName, renderingEngineName);
renditionDef.parameters['mime-type'] = 'text/html';
var htmlRendition= renditionService.render(document, renditionDef);