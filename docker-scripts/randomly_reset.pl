#!/usr/bin/perl

my @env = `cat .env`;
my $hash = {};
foreach my $line(@env) {
  chomp $line;
  my ($name, $value) = split(/=/, $line);
  $hash->{$name} = $value;
}

my $id = &choose_server;
my $key = "KAFKA${id}_IP";
my $value = $hash->{$key};
print "Changing ip for kafka-$id...\n";
print "OLD $key => $value\n";
my $new_value = &convert_ip($value);
print "NEW $key => $new_value\n";

$hash->{$key} = $new_value;
&write_env($hash);
print "Done!\n";


`docker-compose stop kafka-$id`;
`docker-compose up -d`;
print "Wating for kafka-$id to be up...\n";
`docker-compose logs -f kafka-$id | tee /tmp/server.log | sed '/INFO.*KafkaServer id=.* started.*kafka.server.KafkaServer/ q'`;
print "Server kafka-$id started. ($value => $new_value) \n";

sub choose_server{
  return int(rand(3)) + 1;
}

sub convert_ip($) {
  my $ip = shift;
  my $firstN = substr($ip, 0, length($ip) - 3);
  my $last3 = substr($ip, -3);
  if($last3 > 200) {
    return $firstN . ($last3 - 100);
  } else {
    return $firstN . ($last3 + 100);
  }

}

sub write_env($) {
  my $hash = shift;
  open FH, ">.env";
  foreach $key(sort (keys %$hash)) {
    my $value = $hash->{$key};
    print FH "$key=$value\n";
  }
  close FH;
}
