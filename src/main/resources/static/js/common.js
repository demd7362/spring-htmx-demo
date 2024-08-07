htmx.logger = function (elt, event, data) {
    if (console) {
        console.log(event, elt, data);
    }
}

function toggleMode(){
    const isDarkMode = localStorage.getItem('isDarkMode');
    if (isDarkMode === null) {
        localStorage.setItem('isDarkMode', "false");
    } else if (JSON.parse(isDarkMode)) {
        setDarkMode();
    } else {
        setLightMode();
    }
    if ($('#waterSheet').attr('href').includes('dark')) {
        setLightMode();
    } else {
        setDarkMode();
    }
}

/* htmx에서는 hx-boost로 이동 시 onload가 작동하지 않음 */
$(function () {

})

function setLightMode() {
    $('#waterSheet').attr('href', 'https://cdn.jsdelivr.net/npm/water.css@2/out/light.min.css');
    $('#theme').html(`<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-sun"><circle cx="12" cy="12" r="5"></circle><line x1="12" y1="1" x2="12" y2="3"></line><line x1="12" y1="21" x2="12" y2="23"></line><line x1="4.22" y1="4.22" x2="5.64" y2="5.64"></line><line x1="18.36" y1="18.36" x2="19.78" y2="19.78"></line><line x1="1" y1="12" x2="3" y2="12"></line><line x1="21" y1="12" x2="23" y2="12"></line><line x1="4.22" y1="19.78" x2="5.64" y2="18.36"></line><line x1="18.36" y1="5.64" x2="19.78" y2="4.22"></line></svg>`);
    localStorage.setItem('isDarkMode', "false");
}

function setDarkMode() {
    $('#waterSheet').attr('href', 'https://cdn.jsdelivr.net/npm/water.css@2/out/dark.min.css');
    $('#theme').html(`<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-moon"><path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z"></path></svg>`);
    localStorage.setItem('isDarkMode', "true");
}


function navigate(href) {
    location.href = href;
}

function openDialog(title, content, callback) {
    $('#dialogTitle').text(title);
    $('#dialogContent').text(content);
    $('#dialog')[0].showModal();
    $('#closeDialog').off('click').on('click', function () {
        $('#dialog')[0].close();
        callback?.();
    });
}

function removeLastChild(selector) {
    for(let i = 0; i < 2; i++){
        $(selector).children().last().remove();
    }
}

function collectJsonProperties(selector) {
    const $inputs = $(selector);
    let properties = [];
    for (let i = 0; i < $inputs.length; i++) {
        const $input = $inputs.eq(i);
        const jsonType = $input.prev().val();
        const attribute = $input.val(); // json 리턴받을 시의 key 값

        const duplicateKeyExists = properties.some(prop => {
            return prop[jsonType] === attribute;
        });

        if (duplicateKeyExists) {
            openDialog('주의', '중복되는 key값이 있습니다.', () => $input.focus());
            return "[]";
        }

        if (!attribute.trim()) {
            openDialog('에러', 'Key 값을 입력해주세요.', () => $input.focus());
            return "[]";
        }

        properties.push({
            [jsonType]: attribute
        });
    }
    return JSON.stringify(properties);
}

function clickMenu(that){
    $(that).next()[0].click();
}
