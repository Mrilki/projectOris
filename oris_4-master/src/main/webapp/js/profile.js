$(document).ready(function () {
    const contextPath = $('meta[name="context-path"]').attr('content');
    const authorId = $('.profile-container').data('user-id')

    $('.show-comments-button').click(function () {
        let dayId = $(this).data('day-id');
        let commentsSection = $('#comments-' + dayId);

        if (commentsSection.is(':visible')) {
            commentsSection.slideUp();
        } else {
            loadComments(dayId, commentsSection);
            commentsSection.slideDown();
        }
    });

    $(document).on('submit', '.add-comment-form', function (event) {
        event.preventDefault();
        let form = $(this);
        let dayId = form.attr('id').split('-')[3];
        let commentText = form.find('input[name="commentText"]').val();

        addComment(dayId, commentText, form);
    });

    function loadComments(dayId, commentsSection) {
        $.ajax({
            url: `${contextPath}/comments`,
            method: 'POST',
            data: {
                action: 'getComments',
                dayId: dayId
            },
            success: function (comments) {
                let commentsList = $('#comments-list-' + dayId);
                commentsList.empty();
                comments.forEach(function (comment) {
                    commentsList.append('<p>(автор: ' + comment.author + ')<br>' + comment.text + '</p>');
                });
            },
            error: function () {
                alert('Ошибка при загрузке комментариев.');
            }
        });
    }

    function addComment(dayId, commentText, form) {
        $.ajax({
            url: `${contextPath}/comments`,
            method: 'POST',
            data: {
                action: 'addComment',
                dayId: dayId,
                commentText: commentText,
                authorId: authorId
            },
            success: function (response) {
                if (response.success) {
                    form.find('input[name="commentText"]').val('');
                    loadComments(dayId, $('#comments-' + dayId));
                } else {
                    alert('Ошибка при добавлении комментария.');
                }
            },
            error: function () {
                alert('Ошибка при добавлении комментария.');
            }
        });
    }
});