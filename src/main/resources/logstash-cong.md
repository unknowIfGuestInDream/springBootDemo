input {
    tcp {
	 mode => "server"
     host =>"localhost"
     port => 4560
     codec => json_lines
        }
}

output {
     elasticsearch {
	    action => "index"
        hosts => "localhost:9200"
        index => "springbootdemo-%{+YYYY.MM.dd}"
     }
     stdout { codec=> rubydebug }
}