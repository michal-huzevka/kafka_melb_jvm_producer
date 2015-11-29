$(function() {
    $("#findFlights").click(startTimer);

});
var count = 0;
var timer;
var id="";
function startTimer() {
    if (timer != null) {
        return;
    }
    count = 0;
    $("#results").css("display", "inline-block");
    $("#results tbody").html("");
    var flightSearch = {
        from : $("#from").val(),
        to : $("#to").val(),
        date : $("#date").val()
    }
    $.ajax({
      type: "POST",
      headers: {
          'Content-Type': 'application/json'
      },
      url: "/flight_search",
      data: JSON.stringify(flightSearch)
    }).done(function(result) {
        id = result;
    });
    timer = setInterval(getLatestFlights, 200);
}
function getLatestFlights() {
    $.get("/flight?id=" + id, function(data) {
        for (var i =0; i<data.length; i++) {
            var flight = data[i];
            var row = "<tr>";
            row += "<td>" + flight.price + "</td>";
            row += "<td>" + flight.airline + "</td>";
            row += "<td>" + flight.departureTime + "</td>";
            row += "<td>" + flight.arrivalTime + "</td>";
            row += "</tr>";
            $("#results tbody").append(row);
        }
    }, "json");
    count++;
    if (count >= 15) {
        window.clearInterval(timer);
        timer = null;
    }
}