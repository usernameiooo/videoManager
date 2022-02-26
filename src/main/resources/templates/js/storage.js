
    function putJson(name,value) {
        //JSON对象转JSON字符串
        value = JSON.stringify(value); //转化为JSON字符串
        localStorage.setItem(name,value);
    }
    function getJson(name) {
//JSON字符串转JSON对象
        return JSON.parse(localStorage.getItem(name));
    }
    function putString(name,value){
        localStorage.setItem(name,value);
    }
    function  getString(name){
    return localStorage.getItem(name);
    }
    function clearStorage(name){
      localStorage.removeItem(name);
    }

