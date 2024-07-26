htmx.logger = function (elt, event, data) {
    if (console) {
        console.log(event, elt, data);
    }
}

$(function () {
    const isDarkMode = localStorage.getItem('isDarkMode');
    if (isDarkMode === null) {
        localStorage.setItem('isDarkMode', "false");
    } else if (JSON.parse(isDarkMode)) {
        setDarkMode();
    } else {
        setLightMode();
    }
    $('#theme').on('click', function () {
        if ($('#waterSheet').attr('href').includes('dark')) {
            setLightMode();
        } else {
            setDarkMode();
        }
    });
    $('form').each(function (form) {
        form.addEventListener('submit', function (event) {
            event.preventDefault();
            // HTMX가 폼 전송을 처리하도록 유지
            htmx.trigger(form, 'submit');
        });
    });
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
    const result = [];
    $inputs.each(function () {
        const jsonType = $(this).prev().val();
        const attribute = $(this).val();
        if(!attribute.trim()){
            openDialog('에러','Key 값을 입력해주세요.', () => $(this).focus());
            return;
        }
        result.push({
            [jsonType]: attribute
        })
    });
    return JSON.stringify(result);
}

