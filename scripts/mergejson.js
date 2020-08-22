let menujson = require('./new_bizmenu');
let basic_new_bizmenujson = require('./basic_new_bizmenu');

for (let key in menujson) {
    let contents = menujson[key];
    //如果目录没有,就加目录
    if (!basic_new_bizmenujson[key]) {
        basic_new_bizmenujson[key] = contents;
    }
    for (let submeuKey in contents.submenu) {
        basic_new_bizmenujson[key].submenu[submeuKey] = contents.submenu[submeuKey];
    }
}

console.log(JSON.stringify(basic_new_bizmenujson));
