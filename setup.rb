module OS
    def OS.windows?
      (/cygwin|mswin|mingw|bccwin|wince|emx/ =~ RUBY_PLATFORM) != nil
    end
  
    def OS.mac?
     (/darwin/ =~ RUBY_PLATFORM) != nil
    end
  
    def OS.unix?
      !OS.windows?
    end
  
    def OS.linux?
      OS.unix? and not OS.mac?
    end
  
    def OS.jruby?
      RUBY_ENGINE == 'jruby'
    end
  end  


`cd docker-compose`
`docker-compose up -d`
output = `rpk version`
unless output.starts_with? "rpk version"
    if OS.mac?
        `brew install redpanda-data/tap/redpanda`
    elsif OS.linux?
        flag = false
        while not flag
            puts "installing redpanda CLI... type x86 if you're on an x86/x64 machine, or arm for ARM, or exit to cancel."
            input = gets.chomp.downcase
            if input == "x86":
                flag = true
                `curl -LO https://github.com/redpanda-data/redpanda/releases/latest/download/rpk-linux-amd64.zip && 
                mkdir -p ~/.local/bin &&
                export PATH="~/.local/bin:$PATH" &&
                unzip rpk-linux-amd64.zip -d ~/.local/bin/`
            elsif input == "amd":
                flag = true
                `curl -LO https://github.com/redpanda-data/redpanda/releases/latest/download/rpk-linux-arm64.zip &&
                mkdir -p ~/.local/bin &&
                export PATH="~/.local/bin:$PATH" &&
                unzip rpk-linux-arm64.zip -d ~/.local/bin/`
            elsif input == "exit":
                return
            end
        end
        `rpk profile create quickstart --from-profile rpk-profile.yaml`
        output = `rpk topic list`
    else
        puts("you're probably using windows or some hipster OS, please install the RPK CLI and rerun this")
    end
end
output = `rpk topic list`
unless output.include? "people"
    `rpk topic create people -p 3`
end
`cd ../`
`maven clean install`