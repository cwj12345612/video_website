let play_search = window.location.search;  /* 接收传递过来的参数*/

let play_uri = decodeURI(play_search);


let play_first = play_uri.indexOf("?");
let play_last = play_uri.indexOf("film");
let play_lastIndexOf = play_uri.lastIndexOf("/");
let play_VideoPath = play_uri.substring(play_last);
// /*  获取影片名称*/
let play_$Title = play_uri.substring(play_first + 1, play_last-1);

let play_VideoName = play_$Title + " / " + play_uri.substring(play_lastIndexOf + 1);
