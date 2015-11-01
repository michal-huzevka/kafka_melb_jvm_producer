require 'rubygems'
require 'net/http'
require 'uri'
require 'json'

base_event_data = {
  make: "rocket ship",
  model: "awesome",
  year: 1979
}

puts 'Started at'
puts Time.now.strftime('%H:%M:%S.%L')

10.times do |i|
  #sleep 0.02

  %x(aws sns publish --topic-arn arn:aws:sns:ap-southeast-2:820906056718:cardetails --message '#{base_event_data.to_json}')
  print '.'
end

print "\n"
puts 'Finished at'
puts Time.now.strftime('%H:%M:%S.%L')

