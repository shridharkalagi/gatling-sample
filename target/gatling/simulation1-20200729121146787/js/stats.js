var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "2",
        "ok": "2",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "297",
        "ok": "297",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "4255",
        "ok": "4255",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "2276",
        "ok": "2276",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "1979",
        "ok": "1979",
        "ko": "-"
    },
    "percentiles1": {
        "total": "2276",
        "ok": "2276",
        "ko": "-"
    },
    "percentiles2": {
        "total": "3266",
        "ok": "3266",
        "ko": "-"
    },
    "percentiles3": {
        "total": "4057",
        "ok": "4057",
        "ko": "-"
    },
    "percentiles4": {
        "total": "4215",
        "ok": "4215",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 1,
    "percentage": 50
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 1,
    "percentage": 50
},
    "group4": {
    "name": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "0.4",
        "ok": "0.4",
        "ko": "-"
    }
},
contents: {
"req_myrequest1-f9178": {
        type: "REQUEST",
        name: "myRequest1",
path: "myRequest1",
pathFormatted: "req_myrequest1-f9178",
stats: {
    "name": "myRequest1",
    "numberOfRequests": {
        "total": "1",
        "ok": "1",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "4255",
        "ok": "4255",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "4255",
        "ok": "4255",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "4255",
        "ok": "4255",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "0",
        "ok": "0",
        "ko": "-"
    },
    "percentiles1": {
        "total": "4255",
        "ok": "4255",
        "ko": "-"
    },
    "percentiles2": {
        "total": "4255",
        "ok": "4255",
        "ko": "-"
    },
    "percentiles3": {
        "total": "4255",
        "ok": "4255",
        "ko": "-"
    },
    "percentiles4": {
        "total": "4255",
        "ok": "4255",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 0,
    "percentage": 0
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 1,
    "percentage": 100
},
    "group4": {
    "name": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "0.2",
        "ok": "0.2",
        "ko": "-"
    }
}
    },"req_myrequest1-redi-c5ea6": {
        type: "REQUEST",
        name: "myRequest1 Redirect 1",
path: "myRequest1 Redirect 1",
pathFormatted: "req_myrequest1-redi-c5ea6",
stats: {
    "name": "myRequest1 Redirect 1",
    "numberOfRequests": {
        "total": "1",
        "ok": "1",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "297",
        "ok": "297",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "297",
        "ok": "297",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "297",
        "ok": "297",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "0",
        "ok": "0",
        "ko": "-"
    },
    "percentiles1": {
        "total": "297",
        "ok": "297",
        "ko": "-"
    },
    "percentiles2": {
        "total": "297",
        "ok": "297",
        "ko": "-"
    },
    "percentiles3": {
        "total": "297",
        "ok": "297",
        "ko": "-"
    },
    "percentiles4": {
        "total": "297",
        "ok": "297",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 1,
    "percentage": 100
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group4": {
    "name": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "0.2",
        "ok": "0.2",
        "ko": "-"
    }
}
    }
}

}

function fillStats(stat){
    $("#numberOfRequests").append(stat.numberOfRequests.total);
    $("#numberOfRequestsOK").append(stat.numberOfRequests.ok);
    $("#numberOfRequestsKO").append(stat.numberOfRequests.ko);

    $("#minResponseTime").append(stat.minResponseTime.total);
    $("#minResponseTimeOK").append(stat.minResponseTime.ok);
    $("#minResponseTimeKO").append(stat.minResponseTime.ko);

    $("#maxResponseTime").append(stat.maxResponseTime.total);
    $("#maxResponseTimeOK").append(stat.maxResponseTime.ok);
    $("#maxResponseTimeKO").append(stat.maxResponseTime.ko);

    $("#meanResponseTime").append(stat.meanResponseTime.total);
    $("#meanResponseTimeOK").append(stat.meanResponseTime.ok);
    $("#meanResponseTimeKO").append(stat.meanResponseTime.ko);

    $("#standardDeviation").append(stat.standardDeviation.total);
    $("#standardDeviationOK").append(stat.standardDeviation.ok);
    $("#standardDeviationKO").append(stat.standardDeviation.ko);

    $("#percentiles1").append(stat.percentiles1.total);
    $("#percentiles1OK").append(stat.percentiles1.ok);
    $("#percentiles1KO").append(stat.percentiles1.ko);

    $("#percentiles2").append(stat.percentiles2.total);
    $("#percentiles2OK").append(stat.percentiles2.ok);
    $("#percentiles2KO").append(stat.percentiles2.ko);

    $("#percentiles3").append(stat.percentiles3.total);
    $("#percentiles3OK").append(stat.percentiles3.ok);
    $("#percentiles3KO").append(stat.percentiles3.ko);

    $("#percentiles4").append(stat.percentiles4.total);
    $("#percentiles4OK").append(stat.percentiles4.ok);
    $("#percentiles4KO").append(stat.percentiles4.ko);

    $("#meanNumberOfRequestsPerSecond").append(stat.meanNumberOfRequestsPerSecond.total);
    $("#meanNumberOfRequestsPerSecondOK").append(stat.meanNumberOfRequestsPerSecond.ok);
    $("#meanNumberOfRequestsPerSecondKO").append(stat.meanNumberOfRequestsPerSecond.ko);
}
