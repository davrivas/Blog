$(document).ready(function () {
    $('#contenido, #comentario').trumbowyg({
        lang: 'es',
        btns: [
            ['viewHTML'],
            ['undo', 'redo'], // Only supported in Blink browsers
            ['formatting'],
            ['strong', 'em', 'del'],
            ['superscript', 'subscript'],
            ['link'],
            ['insertImage'],
            ['upload'],
            ['justifyLeft', 'justifyCenter', 'justifyRight', 'justifyFull'],
            ['unorderedList', 'orderedList'],
            ['horizontalRule'],
            ['removeformat'],
            ['fullscreen']
        ],
        tagsToRemove: ['script', 'link', 'h1'],
        plugins: {
            upload: {
                serverPath: 'https://api.imgur.com/3/image',
                fileFieldName: 'image',
                headers: {
                    'Authorization': 'Client-ID 9e57cb1c4791cea' // esto puede cambiar
                },
                urlPropertyName: 'data.link'
            }
        }
    });
});