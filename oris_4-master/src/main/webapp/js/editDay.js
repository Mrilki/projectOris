$(document).ready(function() {
    const contextPath = "${pageContext.request.contextPath}";
    const dayId = $("#dayId").val();

    $('#saveButton').click(function() {
        const date = $('#date').val();
        const text = $('#text').val();

        $.ajax({
            url: `${contextPath}/editDay`,
            type: 'POST',
            data: {
                action: 'update',
                dayId: dayId,
                date: date,
                text: text
            },
            success: function(response) {
                if (response.success) {
                    alert('Пост обновлен успешно!');
                } else {
                    alert('Неизвестный ответ от сервера.');
                }
            },
            error: function(xhr, status, error) {
                alert('Ошибка при обновлении поста: ' + xhr.responseText);
            }
        });
    });

    $(document).on('click', '#deleteImageButton', function() {
        if (confirm('Вы уверены, что хотите удалить изображение?')) {

            $.ajax({
                url: `${contextPath}/editDay`,
                type: 'POST',
                data: {
                    action: 'deleteImage',
                    dayId: dayId
                },
                success: function(response) {
                    if (response.success) {
                        alert('Изображение удалено успешно!');
                        $('#existingImageContainer').remove();
                        if (!$('#addImageContainer').length) {
                            $('#imageSection').append(`
                  <div class="form-group" id="addImageContainer">
                    <label for="image">Добавить изображение:</label>
                    <input type="file" name="image" id="image">
                  </div>
                `);
                        }
                    } else {
                        alert('Неизвестный ответ от сервера.');
                    }
                },
                error: function(xhr) {
                    alert('Ошибка при удалении изображения: ' + xhr.responseText);
                }
            });
        }
    });

    $(document).on('change', '#image', function() {
        const fileInput = this;
        const file = fileInput.files[0];
        if (file) {

            const formData = new FormData();
            formData.append('action', 'uploadImage');
            formData.append('dayId', dayId);
            formData.append('image', file);

            $.ajax({
                url: `${contextPath}/editDay`,
                type: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                success: function(response) {
                    if (response.success) {
                        alert('Изображение загружено успешно!');
                        $('#addImageContainer').remove();
                        $('#imageSection').append(`
                <div class="existing-image" id="existingImageContainer">
                  <p>Текущее изображение:</p>
                      <img src="/uploaded/files?id=` + response.imageId + `" alt="Изображение поста" width="300" id="existingImage">
                  <button type="button" class="delete-button" id="deleteImageButton">Удалить изображение</button>
                </div>
              `);
                    } else {
                        alert('Неизвестный ответ от сервера.');
                    }
                },
                error: function(xhr) {
                    alert('Ошибка при загрузке изображения: ' + xhr.responseText);
                }
            });
        }
    });
});
