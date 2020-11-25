function createFullCkeditor(areaId) {
    ClassicEditor
        .create( document.querySelector( `#${areaId}` ), {
            
            toolbar: {
                items: [
                    'heading',
                    '|',
                    'undo',
                    'redo',
                    '|',
                    'fontFamily',
                    'fontSize',
                    'bulletedList',
                    'numberedList',
                    'highlight',
                    '|',
                    'bold',
                    'italic',
                    'link',
                    '|',
                    'alignment',
                    'indent',
                    'outdent',
                    '|',
                    'codeBlock',
                    'blockQuote',
                    'insertTable'
                ]
            },
            language: 'en',

            image: {
                toolbar: [
                    'imageTextAlternative',
                    'imageStyle:full',
                    'imageStyle:side'
                ]
            },
            table: {
                contentToolbar: [
                    'tableColumn',
                    'tableRow',
                    'mergeTableCells'
                ]
            },
            licenseKey: '',
            
        } )
        .then( editor => {
            window.editor = editor;       
        } )
        .catch( error => {
            console.error( 'Oops, something went wrong!' );
            console.error( 'Please, report the following error on https://github.com/ckeditor/ckeditor5/issues with the build id and the error stack trace:' );
            console.warn( 'Build id: hwokwqc9pg96-bews2myya8us' );
            console.error( error );
        } );
}

function createMinCkeditor(areaId) {
    ClassicEditor
        .create( document.querySelector( `#${areaId}` ), {
            
            toolbar: {
                items: [
                    'undo',
                    'redo',
                    '|',
                    'bold',
                    'italic',
                    'link',
                    '|',
                    'alignment',
                    'indent',
                    'outdent',
                ]
            },
            language: 'en',          
        } )
        .then( editor => {
            window.editor = editor;       
        } )
        .catch( error => {
            console.error( 'Oops, something went wrong!' );
            console.error( 'Please, report the following error on https://github.com/ckeditor/ckeditor5/issues with the build id and the error stack trace:' );
            console.warn( 'Build id: hwokwqc9pg96-bews2myya8us' );
            console.error( error );
        } );
}