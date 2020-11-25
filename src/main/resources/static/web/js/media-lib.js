
function getMedia(courseId, lessonNo) {
    $.ajax({
        contentType: 'application/json',
        dataType: 'json',
        url: '/web/api/media',
        type: 'GET',
        data: {
            courseId, lessonNo
        },
        success: (response) => {
            // console.log(response);
            $('#mediaLibrary .list-group.item-list').empty();
            for (let media of response) {
                $('#mediaLibrary .list-group.item-list').append(renderMediaListItem(media))
            }
            setInsertBtn();
            setDeleteBtn();
        },
        error: (error) => {
            console.log(error);
        }
    });
}

function deleteMedia(mediaId) {
    $.ajax({
        contentType: 'application/json',
        dataType: 'json',
        url: `/web/api/media/${mediaId}`,
        type: 'DELETE',
        success: (response) => {
            console.log(response);
            reloadLibrary();
        },
        error: (error) => {
            console.log(error);
        }
    });
}

function renderMediaListLoading(mediaName, processingState) {
    return (
        '<a class="list-group-item list-group-item-action d-flex flex-row justify-content-between">' +
            '<div class="d-flex flex-column">' +
                `<span>${mediaName}</span>` +
                `<span class="text-muted">${processingState}</span>` +
            '</div>' +
            '<div class="loading-container d-flex justify-content-center align-items-center">' +
                '<div class="spinner-border" role="status">' +
                '<span class="sr-only">Loading...</span>' +
                '</div>' + 
            '</div>' +
        '</a>'
    );
}

function renderMediaListItem(media) {
    console.log(media);
    mediaId = media.id;
    mediaName = media.filename;
    mediaType = media.mediaTypeName;
    src = `/resource/${media.code}`;
    caption = media.caption;
    mediaPreviewSrc = src;
    if (mediaPreviewSrc === undefined) mediaPreviewSrc = '/web/img/default-avatar.png';
    return (
        `<a href="" class="list-group-item list-group-item-action d-flex flex-row justify-content-between">` +
            '<div class="d-flex flex-column">' +
            `<span>${mediaName}</span>` +
            `<span class="text-muted">${mediaType}</span>` +
            '</div>' +
            `<img class="media-preview" src="${mediaPreviewSrc}" alt="media-preview">` +
            '<div class="item-list-btn-view">' +
                `<div class="badge badge-warning insert-btn" src="${src}" caption="${caption}" data-toggle="tooltip" title="Chèn vào nội dung"><i class="fas fa-link"></i></div>` +
                `<div id="${mediaId}" class="badge badge-danger delete-btn" data-toggle="tooltip" title="Xóa tệp"><i class="fas fa-times"></i></div>` +
            '</div>' +
        '</a>'
    );
}

function reloadLibrary() {
    let courseId = parseInt($('#courseId').val())
    let lessonNo = parseInt($('#lessonNo').val())
    getMedia(courseId, lessonNo);
}

function uploadMedia() {
    let formData = $('#uploadMediaForm')[0]
    let data = new FormData(formData);
    let fileInp = $('#uploadMediaForm input[type="file"]');
    let filePath = fileInp.val();
    let filename = getFileName(filePath);
    $('#mediaLibrary .list-group.loading-span').empty();
    $('#mediaLibrary .list-group.loading-span').append(renderMediaListLoading(filename, 'Đang tải lên...'))
    $('#uploadMediaModal').modal('hide');
    $.ajax({
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        timeout: 1000000,
        dataType: 'json',
        url: '/web/api/media/upload',
        type: 'POST',
        data: data,
        success: (response) => {
            // console.log(response);
            if ($('#lessonNo').length === 0) window.location.replace(window.location.href.replace('new-lesson', `lesson-${response.lessonId.no}/edit`));
            else {
                $('#mediaLibrary .list-group.loading-span').empty();
                reloadLibrary();
            }
        },
        error: (error) => {
            console.log(error);
        }
    });
}

function setInsertBtn() {
    $('.insert-btn').click(function(event){
        event.preventDefault();
        event.stopPropagation();
        let src = $(this).attr('src');
        let caption = $(this).attr('caption');
        let captionView = caption != null?`<figcaption>${caption}</figcaption>`:'<figcaption>Nhập chú thích cho hình ảnh</figcaption>';
        const content = `<figure class="image"><img src="${src}" alt="Kết nối không ổn định hoặc hình ảnh đã bị xóa">${captionView}</figure>`;
        console.log(content);
        const viewFragment = editor.data.processor.toView( content );
        const modelFragment = editor.data.toModel( viewFragment );
        editor.model.insertContent( modelFragment );
    })
}

function setDeleteBtn() {
    $('.delete-btn').click(function(event){
        event.preventDefault();
        event.stopPropagation();
        let mediaId = $(this).attr('id');
        deleteMedia(mediaId);
    })
}




$(function() {
    reloadLibrary();
})