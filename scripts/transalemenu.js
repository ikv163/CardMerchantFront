let basic_new_bizmenujson = require('./new_bizmenu.json');
var data = [];
for (let cn in basic_new_bizmenujson) {
    let v = basic_new_bizmenujson[cn];
    data.push({menu_id: v.menu_id, menu_name: cn});
    for (let submeuKey in v.submenu) {
       let submenu = v.submenu[submeuKey];
        data.push({menu_id: submenu.menu_id, menu_name: submeuKey});
    }
}


var str = `
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<pre>
`
for (let v of data) {
    str += `    menusname${v.menu_id}=${v.menu_name}\n\n\n\n`;
}
str += `
</pre>
</body>
</html>
`;
console.log(str);

