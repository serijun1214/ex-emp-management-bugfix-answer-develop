$(document).ready(function() {
  // エレメントをjQueryセレクタで取得
  const $zipcode = $("#zipcode");
  const $address = $("#address");
  const $button = $("#address-search-btn");

  $button.on("click", function(event) {
    event.preventDefault();
    const zipcodeValue = $zipcode.val();
    const apiUrl = `https://zipcloud.ibsnet.co.jp/api/search?zipcode=${zipcodeValue}`;

    $.ajax({
      url: apiUrl,
      method: "GET",
      dataType: "json"
    }).done(function(data) {
      
      if (data.status === 200) {
        const addressData = `${data.results[0].address1}${data.results[0].address2}${data.results[0].address3}`;
        $address.val(addressData);
      } else {
        $address.val("");
      }
    }).fail(function(jqXHR, textStatus, errorThrown) {
      console.error("Fetchエラー:", errorThrown);
    });
  });
});
