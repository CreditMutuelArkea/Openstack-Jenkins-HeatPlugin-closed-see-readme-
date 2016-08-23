/**
 * 
 * Copyright 2015 Credit Mutuel Arkea
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 * 
 */
function generateBundle(div, data, uid) {
	var display = generateProperties(data, uid);
	if (data['parameters'] !== undefined && isNotEmpty(data['parameters'])) {
		display += generateParameters(data['parameters'], uid);
	}
	if (data['outputs'] !== undefined && isNotEmpty(data['outputs'])) {
		display += generateOutputs(data['outputs'], uid);
	}
	div.innerHTML = display;
}

function generateProperties(data, uid) {
	var properties = '<table width="100%"><tbody><tr><td colspan="3"><div class="section-header">Properties</div></td></tr>';
	properties += '<tr><td class="setting-leftspace"></td><td class="setting-name">Stack name : </td><td class="setting-main"><input type="text" class="setting-input"';
	if (data["name"] !== '') {
		properties += ' value="' + data["name"] + '" ';
	}
	properties += 'name="name" onchange="updateBundle(\'\', this.name, this.value, \''
			+ uid + '\')"></input></td></tr>';
	properties += '<tr><td colspan="3"><input type="checkbox" name="exist"';
	if (data["exist"] === true) {
		properties += ' checked ';
	}
	properties += ' onchange="updateBundle(\'\', this.name, this.checked, \''
			+ uid
			+ '\')"></input><label class="attach-previous">Delete stack if already exists ?</label></td></tr>';
	properties += '<tr><td colspan="3"><input type="checkbox" name="debug"';
	if (data["debug"] === true) {
		properties += ' checked ';
	}
	properties += ' onchange="updateBundle(\'\', this.name, this.checked, \''
			+ uid
			+ '\')"></input><label class="attach-previous">Debug mode ?</label></td></tr>';
	properties += '</tbody></table>'
	return properties;
}

function generateParameters(data, uid) {
	var param = undefined;
	var inputs = '<table width="100%"><tbody><tr><td colspan="3"><div class="section-header">Parameters</div></td></tr>';
	for (var datum in data) {
		inputs += '<tr><td class="setting-leftspace"></td><td class="setting-name">';
		param = data[datum];
		if (param["label"] !== '') {
			inputs += param["label"];
		} else {
			inputs += datum;
		}
		inputs += ' : </td><td class="setting-main">';
		if (isNotEmpty(data[datum]["constraints"])){
			if(data[datum]["constraints"].length != 0){
				var description = undefined;
				var nbConstraints = data[datum]["constraints"].length;
				var hasAllowedValues = false;
				var hasRange = false;
				var hasAllowedPattern = false;
				var hasLength = false;
				var index = undefined;
				var dualIndexMajor = undefined;
				var dualIndexMinor = undefined;
				for(var i = 0; i<nbConstraints; i++){
					if(data[datum]["constraints"][i]["type"] == "allowed_values"){
						hasAllowedValues = true;
						index = i;
					}else if(data[datum]["constraints"][i]["type"] == "range"){
						hasRange = true;
						index = i;
					}else if(data[datum]["constraints"][i]["type"] == "length"){
						hasLength = true;
						dualIndexMinor = i;
					}else if(data[datum]["constraints"][i]["type"] == "allowed_pattern"){
						hasAllowedPattern = true;
						dualIndexMajor = i;
					}
				}

				if(hasAllowedValues){
					var cType = data[datum]["constraints"][index]["type"];
					var selectedVal = param["value"];
					inputs += '<select name="'+datum+'" ';
					if (param["description"] !== '') {
						inputs += ' title="' + param["description"] + '" ';
					}
					inputs += ' onchange="updateBundle(\'parameters\', this.name, this.options[this.selectedIndex].value, \''
						+ uid + '\')" >';
					var allowed_values = data[datum]["constraints"][index]["allowedValues"];
					for(var j=0; j<allowed_values.length; j++){
						var aValue = allowed_values[j];
						if(aValue == selectedVal){
							inputs += '<option selected="selected" value="'+aValue+'">'+aValue+'</option>';
						}else{
							inputs += '<option value="'+aValue+'">'+aValue+'</option>';
						}
					}
					inputs += '</select></td></tr>';
				}else if(hasRange){
					description = data[datum]["constraints"][index]["description"];
					inputs += '<input type="number" class="setting-input" name="';
					inputs += datum + '"';
					if (param["value"] !== '') {
						inputs += ' value="' + param["value"] + '" ';
					} else if (param["defaultValue"] !== '') {
						inputs += ' value="' + param["defaultValue"] + '" ';
					}
					if (param["description"] !== '') {
						inputs += ' title="' + param["description"] + '" ';
					}
					var min = data[datum]["constraints"][index]["minRange"];
					var max = data[datum]["constraints"][index]["maxRange"];
					inputs += ' min="' + min + '" max="' + max + '" ';
					inputs += ' onchange="updateBundle(\'parameters\', this.name, this.value, \''
							+ uid + '\')" ></input>';
				}else{
					if(hasAllowedPattern){
						if(hasLength){
							description = '';
							if(data[datum]["constraints"][dualIndexMajor]["description"] != undefined){
								description = data[datum]["constraints"][dualIndexMajor]["description"];
							}
							if(data[datum]["constraints"][dualIndexMinor]["description"] != undefined){
								description += ' / '+data[datum]["constraints"][dualIndexMinor]["description"];
							}
							inputs += '<input type="text" class="setting-input" name="';
							inputs += datum + '"';
							inputs += ' id="'+datum+'"';
							if (param["value"] !== '') {
								inputs += ' value="' + param["value"] + '" ';
							} else if (param["defaultValue"] !== '') {
								inputs += ' value="' + param["defaultValue"] + '" ';
							}
							if (param["description"] !== '') {
								inputs += ' title="' + param["description"] + '" ';
							}
							var pattern = data[datum]["constraints"][dualIndexMajor]["allowedPattern"];
							var min = data[datum]["constraints"][dualIndexMinor]["minLength"];
							var max = data[datum]["constraints"][dualIndexMinor]["maxLength"];
							inputs += ' maxlength="' + max + '"'; 
							inputs += ' onblur="checkLengthAndPattern(this.value, this.name, this.id, \'' + min + '\', \'' + max + '\', \'' + pattern + '\',\'' + description + '\')" ';
							inputs += ' onchange="updateBundle(\'parameters\', this.name, this.value, \''
									+ uid + '\')" ></input>';
						}else{
							description = '';
							if(data[datum]["constraints"][dualIndexMajor]["description"] != undefined){
								description = data[datum]["constraints"][dualIndexMajor]["description"];
							}
							inputs += '<input type="text" class="setting-input" name="';
							inputs += datum + '"';
							inputs += ' id="'+datum+'"';
							if (param["value"] !== '') {
								inputs += ' value="' + param["value"] + '" ';
							} else if (param["defaultValue"] !== '') {
								inputs += ' value="' + param["defaultValue"] + '" ';
							}
							if (param["description"] !== '') {
								inputs += ' title="' + param["description"] + '" ';
							}
							var pattern = data[datum]["constraints"][dualIndexMajor]["allowedPattern"];
							inputs += ' onblur="checkPattern(this.value, this.name, this.id, \'' + pattern + '\', \'' + description + '\')" ';
							inputs += ' onchange="updateBundle(\'parameters\', this.name, this.value, \''
									+ uid + '\')" ></input>';
						}
					}else if(hasLength){
						description = '';
						if(data[datum]["constraints"][dualIndexMinor]["description"] != undefined){
							description = data[datum]["constraints"][dualIndexMinor]["description"];
						}
						inputs += '<input type="text" class="setting-input" name="';
						inputs += datum + '"';
						inputs += ' id="'+datum+'"';
						if (param["value"] !== '') {
							inputs += ' value="' + param["value"] + '" ';
						} else if (param["defaultValue"] !== '') {
							inputs += ' value="' + param["defaultValue"] + '" ';
						}
						if (param["description"] !== '') {
							inputs += ' title="' + param["description"] + '" ';
						}
						var min = data[datum]["constraints"][dualIndexMinor]["minLength"];
						var max = data[datum]["constraints"][dualIndexMinor]["maxLength"];
						inputs += ' maxlength="' + max + '"'; 
						inputs += ' onblur="checkLength(this.value, this.name, this.id, \'' + min + '\',\'' + max + '\', \'' + description + '\')" ';
						inputs += ' onchange="updateBundle(\'parameters\', this.name, this.value, \''
								+ uid + '\')" ></input>';
					}else{
						inputs += '<input type="text" class="setting-input" name="';
						inputs += datum + '"';
						if (param["value"] !== '') {
							inputs += ' value="' + param["value"] + '" ';
						} else if (param["defaultValue"] !== '') {
							inputs += ' value="' + param["defaultValue"] + '" ';
						}
						if (param["description"] !== '') {
							inputs += ' title="' + param["description"] + '" ';
						}
						inputs += ' onchange="updateBundle(\'parameters\', this.name, this.value, \''
								+ uid + '\')" ></input>';
					}
				}				
			}else{
				inputs += '<input type="text" class="setting-input" name="';
				inputs += datum + '"';
				if (param["value"] !== '') {
					inputs += ' value="' + param["value"] + '" ';
				} else if (param["defaultValue"] !== '') {
					inputs += ' value="' + param["defaultValue"] + '" ';
				}
				if (param["description"] !== '') {
					inputs += ' title="' + param["description"] + '" ';
				}
				inputs += ' onchange="updateBundle(\'parameters\', this.name, this.value, \''
						+ uid + '\')" ></input>';
			}
		}
		inputs += '<tr>';
		inputs += '<td class="setting-leftspace"></td>';
		inputs += '<td class="setting-name"></td>';
		inputs += '<td><label class="setting-name" id="error'+datum+'"></label></td></tr>';
	}
	inputs += '</tbody></table>';
	return inputs;
}

function generateOutputs(data, uid) {
	var output = undefined;
	var outputs = '<table width="100%"><tbody><tr><td colspan="3"><div class="section-header">Outputs</div></td></tr>';
	for (var datum in data) {
		outputs += '<tr><td class="setting-leftspace"></td><td class="setting-name">';
		output = data[datum];
		outputs += datum
				+ ' : </td><td class="setting-main"><input type="text" class="setting-input" name="';
		outputs += datum + '"';
		outputs += ' title="' + data[datum]['description'] + '" ';
		if (output["value"] !== '') {
			outputs += ' value="' + output["value"] + '" ';
		}
		outputs += ' onchange="updateBundle(\'outputs\', this.name, this.value, \''
				+ uid + '\')" ></input></td></tr>';
	}
	outputs += '</tbody></table>'
	return outputs;
}

function updateBundle(family, name, value, uid) {
	var bundle = JSON.parse(document.getElementById('bundle' + uid).value);

	if (family !== '') {
		bundle[family][name]['value'] = value;
	} else {
		bundle[name] = value;
	}
	document.getElementById('bundle' + uid).value = JSON.stringify(bundle);
}

function checkLength(str, name, id, min, max, description){
	var size = str.length;
	if(size<min || size >max){
		document.getElementById('error'+name).innerHTML = "* "+description;
		document.getElementById('error'+name).style.color = "red";
		document.getElementById(id).value = "";
		document.getElementById(id).style.borderColor = "red";
		return false;
	}else{
		document.getElementById('error'+name).innerHTML = "";
		document.getElementById(id).style.borderColor = "green";
	}
}

function checkPattern(str, name, id, pattern, description){
	var regex = new RegExp(pattern, "g");
	if(!regex.test(str)){
		document.getElementById('error'+name).innerHTML = "* "+description;
		document.getElementById('error'+name).style.color = "red";
		document.getElementById(id).value = "";
		document.getElementById(id).style.borderColor = "red";
	}else{
		document.getElementById('error'+name).innerHTML = "";
		document.getElementById(id).style.borderColor = "green";
	}
}

function checkLengthAndPattern(str, name, id, min, max, pattern, description){
	checkPattern(str, name, id, pattern, description);
	checkLength(str, name, id, min, max, description);
}

function isNotEmpty(obj) {
	for (var data in obj) {
		return true;
	}
	return false;
}

function generateEnv(data) {
	var display='';
	if (data['parameter_defaults'] !== undefined && isNotEmpty(data['parameter_defaults'])) {
		display='parameter_defaults:<br>';
		for(var param in data['parameter_defaults']) {
			display += '&nbsp;&nbsp;' + param + ' : ' + data['parameter_defaults'][param] + '<br>';
		}
		display += '<br>';
	}
	if (data['parameters'] !== undefined && isNotEmpty(data['parameters'])) {
		display +='parameters:<br>';
		for(var param in data['parameters']) {
			display += '&nbsp;&nbsp;' + param + ' : ' + data['parameters'][param] + '<br>';
		}
	}
	
	return display;
	
}

function updateParametersInBundle(uid, data) {
	
	try {
		var bundle = JSON.parse(document.getElementById('bundle' + uid).value);
	
		if (data['parameter_defaults'] !== undefined && isNotEmpty(data['parameter_defaults'])) {
			for(var param in data['parameter_defaults']) {
				if(bundle['parameters'][param] != undefined) {
					bundle['parameters'][param]['value'] = data['parameter_defaults'][param];
				}
			}
		}
		
		if (data['parameters'] !== undefined && isNotEmpty(data['parameters'])) {
			for(var param in data['parameters']) {
				if(bundle['parameters'][param] != undefined) {
					bundle['parameters'][param]['value'] = data['parameters'][param];
				}
			}
		}
		
		document.getElementById('bundle' + uid).value = JSON.stringify(bundle);
	} catch(exception) {
		alert(exception);
	}
}

function updateToolTip(id, data) {
	document.getElementById(id).setAttribute('tooltip', data);
	document.getElementById(id).setAttribute('title', data);
}