function fetchThisMonthRevenueChart() {
  $.ajax({
    contentType: "application/json",
    dataType: "json",
    url: "/admin/api/chart/this-revenue",
    type: "GET",
    success: (response, status) => {
      $('div.this-revenue').html(response + ' VNĐ')
    },
    error: (error) => {
      console.log(error);
    },
  });
}
fetchThisMonthRevenueChart()

function fetchAverageRevenueChart() {
  $.ajax({
    contentType: "application/json",
    dataType: "json",
    url: "/admin/api/chart/average-revenue",
    type: "GET",
    success: (response, status) => {
      $('div.average-revenue').html(response + ' VNĐ')
    },
    error: (error) => {
      console.log(error);
    },
  });
}
fetchAverageRevenueChart()


function fetchAverageAccessChart() {
  $.ajax({
    contentType: "application/json",
    dataType: "json",
    url: "/admin/api/chart/average-access",
    type: "GET",
    success: (response, status) => {
      $('div.average-access').html(response)
    },
    error: (error) => {
      console.log(error);
    },
  });
}
fetchAverageAccessChart()


function fetchThisMonthAccessChart() {
  $.ajax({
    contentType: "application/json",
    dataType: "json",
    url: "/admin/api/chart/this-access",
    type: "GET",
    success: (response, status) => {
      $('div.this-access').html(response)
    },
    error: (error) => {
      console.log(error);
    },
  });
}
fetchThisMonthAccessChart()