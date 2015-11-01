require 'rubygems'
require 'uri'
require 'json'
require 'typhoeus'

if ARGV.length != 2
  puts "usage `ruby send_events.rb {{no_of_requests}} {{no_of_threads}}`"
  exit
end

requests, threads = ARGV

base_event_data = {
  make: "rocket ship",
  model: "awesome",
  year: 1979
}

uri = URI.parse('http://54.66.246.207:8080/car_details')
header = {'Content-Type' => 'application/json'}

hydra = Typhoeus::Hydra.new max_concurrency: threads.to_i

requests.to_i.times do |i|
  augmented_event = base_event_data.merge({ uuid: i.to_s })
  request = Typhoeus::Request.new(
              uri, {
              method: :post,
              headers: header,
              body: augmented_event.to_json
            })
  hydra.queue request
end

hydra.run
