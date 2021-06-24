let UpFilm_FilmAll = [];


$("#button").click(function () {
    clear();
    Auto();
    let a = valueNull();
    let b = pictA();
    let c = filmType();
    let d = filmExists();
    let e = select();
    let f = PictB();
        if (!a) {
            return false;
        }
        if (!b) {
            return false;
        }
        if (!c) {

            return false;
        }
        if (!d) {
            return false;
        }
        if (!e) {
            return false;
        }
        if(!f){
            return false;
        }

    return true;
});







